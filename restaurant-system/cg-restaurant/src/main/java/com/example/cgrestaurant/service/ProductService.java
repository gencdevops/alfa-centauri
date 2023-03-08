package com.example.cgrestaurant.service;


import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.dto.response.ProductStatusCacheDto;
import com.example.cgcommon.request.ProductPricesRequestDto;
import com.example.cgrestaurant.configuration.CacheClient;
import com.example.cgrestaurant.dto.CreateProductPriceRequestDto;
import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.request.UpdateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.exception.BranchNotFoundException;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.exception.ProductPriceNotFoundException;
import com.example.cgrestaurant.mapper.ProductMapper;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.model.ProductPrice;
import com.example.cgrestaurant.repository.BranchRepository;
import com.example.cgrestaurant.repository.ProductPriceRepository;
import com.example.cgrestaurant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BranchRepository branchRepository;
    private final CacheClient cacheClient;

    private final ProductPriceRepository productPriceRepository;

    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) {
        Product createdProduct = productMapper.convertProductFromCreateProductRequestDto(createProductRequestDto);
        createdProduct = productRepository.save(createdProduct);

   ProductStatusCacheDto productStatusCacheDto = ProductStatusCacheDto.builder()
           .productId(createdProduct.getId().toString())
           .productStatus(createdProduct.getProductStatus().toString())
           .build();
        cacheClient.set(createdProduct.getId().toString() ,productStatusCacheDto);

        ProductPrice productPrice = ProductPrice.builder()
                .productId(createdProduct.getId())
                .price(createProductRequestDto.defaultPrice()).build();
        productPriceRepository.save(productPrice);

        log.info("created supplier : " + createdProduct);
        return productMapper.convertProductResponseDtoFromProduct(createdProduct);
    }

    public ProductResponseDto getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::convertProductResponseDtoFromProduct)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
    }

    public Product findByProductId(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
    }


    public List<ProductResponseDto> getAllProduct() {
        return productRepository.findAll().stream()
                .map(productMapper::convertProductResponseDtoFromProduct).toList();
    }

    public ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto updateProductRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
        product.setProductName(updateProductRequestDto.productName());

        return productMapper.convertProductResponseDtoFromProduct(productRepository.save(product));
    }

    public void createProductPrice(UUID id, UUID branchId, CreateProductPriceRequestDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
        Branch branch = branchRepository
                .findById(branchId).orElseThrow(() -> new BranchNotFoundException("Branch not found."));

        ProductPrice productPrice = ProductPrice.builder()
                .productId(product.getId())
                .branchId(branch.getId())
                .price(request.price()).build();

        productPriceRepository.save(productPrice);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public ProductPriceResponseDto getProductPrice(UUID productId, UUID branchID) {
        Optional<ProductPrice> branchProductPrice = productPriceRepository.getByProductIdAndBranchId(productId, branchID);
        return branchProductPrice
                .map(productMapper::convertProductPriceResponseDtoFromProductPrice)
                .orElseGet(() -> productPriceRepository.getByProductIdAndBranchIdIsNull(productId)
                        .map(productMapper::convertProductPriceResponseDtoFromProductPrice)
                        .orElseThrow(() -> new ProductPriceNotFoundException("Product price not found.")));

    }

    public List<ProductPriceResponseDto> getProductPrices(UUID branchId, ProductPricesRequestDto productPricesRequestDto) {

        List<ProductPrice> branchProductPrices = productPriceRepository
                .getByBranchIdAndProductIdIn(branchId, productPricesRequestDto.getProductIds());

        List<UUID> missingProductIds = productPricesRequestDto.getProductIds().stream().
                filter(productId -> branchProductPrices.stream()
                        .noneMatch(branchProductPrice -> branchProductPrice.getProductId().equals(productId))).toList();
        if (CollectionUtils.isEmpty(missingProductIds)) {
            return branchProductPrices.stream()
                    .map(productMapper::convertProductPriceResponseDtoFromProductPrice).toList();
        }


        List<ProductPrice> productPrices = productPriceRepository.getByProductIdInAndBranchIdIsNull(missingProductIds);

        return Stream.concat(branchProductPrices.stream(), productPrices.stream())
                .map(productMapper::convertProductPriceResponseDtoFromProductPrice).toList();
    }





}

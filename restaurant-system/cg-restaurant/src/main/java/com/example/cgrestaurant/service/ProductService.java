package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.request.UpdateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.exception.BranchNotFoundException;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.mapper.ProductMapper;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SupplierService supplierService;

    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) {
        Product createdProduct = productMapper.convertProductFromCreateProductRequestDto(createProductRequestDto);
        createdProduct.setSupplier(supplierService.getSupplierByID(createProductRequestDto.supplierId()));
        createdProduct = productRepository.save(createdProduct);
        log.info("created supplier : " + createdProduct);
       return  productMapper.convertProductResponseDtoFromProduct(createdProduct);
    }

    public ProductResponseDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::convertProductResponseDtoFromProduct)
                .orElseThrow(() -> new ProductNotFoundException("Product npt found."));
    }

    public Product getProductByName(String productName) {
         return productRepository.findByProductName(productName).orElseThrow(() -> new ProductNotFoundException("Product npt found."));
    }



    public List<ProductResponseDto> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::convertProductResponseDtoFromProduct)
                .toList();
    }

    public ProductResponseDto updateProduct(Long id, UpdateProductRequestDto updateProductRequestDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));
        product.setProductName(updateProductRequestDto.productName());
        product.setDefaultPrice(updateProductRequestDto.defaultPrice());
        return   productMapper.convertProductResponseDtoFromProduct(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

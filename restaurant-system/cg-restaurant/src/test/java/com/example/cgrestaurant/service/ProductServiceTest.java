package com.example.cgrestaurant.service;

import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.model.ProductStatus;
import com.example.cgcommon.request.ProductPricesRequestDto;
import com.example.cgrestaurant.dto.CreateProductPriceRequestDto;
import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.request.UpdateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.mapper.ProductMapper;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.model.ProductPrice;
import com.example.cgrestaurant.repository.BranchRepository;
import com.example.cgrestaurant.repository.ProductPriceRepository;
import com.example.cgrestaurant.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;
    @Mock
    private ProductPriceRepository productPriceRepository;
    @Mock
    private ProductMapper productMapper;

    @Test
    void shouldReturnResponseWhenCreateProduct() {
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        UUID supplierId = UUID.randomUUID();

        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto(productName, defaultPrice, supplierId, ProductStatus.ACTIVE);
        Product product = new Product(null, productName, supplierId, ProductStatus.ACTIVE);

        when(productMapper.convertProductFromCreateProductRequestDto(createProductRequestDto)).thenReturn(product);
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(new ProductPrice());
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.convertProductResponseDtoFromProduct(any(Product.class))).thenReturn(new ProductResponseDto(productName, defaultPrice, ProductStatus.ACTIVE));

        ProductResponseDto result = productService.createProduct(createProductRequestDto);
        assertEquals(productName, result.productName());
        assertEquals(defaultPrice, result.defaultPrice());
        assertEquals(ProductStatus.ACTIVE, result.productStatus());

        verify(productRepository).save(any());
        verify(productPriceRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        UUID id = UUID.randomUUID();
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    void shouldReturnProductWhenProductExist() {
        UUID id = UUID.randomUUID();
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .id(id)
                .productStatus(productStatus)
                .build();
        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));
        when(productMapper.convertProductResponseDtoFromProduct(product)).thenReturn(new ProductResponseDto(productName, defaultPrice, productStatus));

        ProductResponseDto productResponseDto = productService.getProductById(id);
        assertEquals(productName, productResponseDto.productName());
        assertEquals(defaultPrice, productResponseDto.defaultPrice());
        assertEquals(productStatus, productResponseDto.productStatus());
    }

    @Test
    void shouldReturnProductWhenProductIdExist() {
        UUID id = UUID.randomUUID();
        String productName = RandomStringUtils.randomAlphabetic(10);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .id(id)
                .productStatus(productStatus)
                .build();
        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));

        Product actual = productService.findByProductId(id);
        assertEquals(id, actual.getId());
        assertEquals(productName, actual.getProductName());
        assertEquals(productStatus, actual.getProductStatus());
    }

    @Test
    void shouldReturnAllProducts() {
        UUID id = UUID.randomUUID();
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .id(id)
                .productStatus(productStatus)
                .build();
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.convertProductResponseDtoFromProduct(product)).thenReturn(new ProductResponseDto(productName, defaultPrice, productStatus));

        List<ProductResponseDto> actual = productService.getAllProduct();
        assertEquals(1, actual.size());
    }
//
    @Test
    void shouldUpdateProduct() {
        UUID id =UUID.randomUUID();
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .id(id)
                .productStatus(productStatus)
                .build();

        UpdateProductRequestDto updateProductRequestDto = new UpdateProductRequestDto(productName, defaultPrice, productStatus);
        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.convertProductResponseDtoFromProduct(product)).thenReturn(new ProductResponseDto(productName, defaultPrice, productStatus));

        ProductResponseDto actual = productService.updateProduct(id,updateProductRequestDto );

        assertEquals(productName, actual.productName());
        assertEquals(defaultPrice, actual.defaultPrice());
        assertEquals(productStatus, actual.productStatus());
    }

    @Test
    void shouldDeleteProduct() {
        UUID id = UUID.randomUUID();
        productService.deleteProduct(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    void shouldCreateProductPrice() {
        UUID productId = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        CreateProductPriceRequestDto request = new CreateProductPriceRequestDto(BigDecimal.TEN);
        Product product = Product.builder()
                .supplierId(UUID.randomUUID())
                .id(productId)
                .productStatus(ProductStatus.ACTIVE).build();

        Branch branch = Branch.builder()
                .supplierId(branchId)
                .build();

        when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.createProductPrice(productId, branchId, request);
        verify(productPriceRepository).save(any(ProductPrice.class));
    }

    @Test
    void shouldGetProductPrice() {
        UUID productId = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();

        ProductPrice productPrice = ProductPrice.builder()
                .priceId(UUID.randomUUID())
                .productId(productId)
                .branchId(branchId)
                .price(BigDecimal.TEN)
                .build();

        ProductPriceResponseDto productPriceResponseDto = new ProductPriceResponseDto(branchId, productId, BigDecimal.TEN);

        when(productPriceRepository.getByProductIdAndBranchId(productId, branchId)).thenReturn(Optional.of(productPrice));
        when(productMapper.convertProductPriceResponseDtoFromProductPrice(productPrice)).thenReturn(productPriceResponseDto);

        ProductPriceResponseDto result = productService.getProductPrice(productId, branchId);
        assertEquals(productPriceResponseDto, result);
    }

    @Test
    void shouldGetProductPrices() {
        UUID productPriceId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();

        ProductPrice productPrice = ProductPrice.builder()
                .priceId(productPriceId)
                .productId(productId)
                .branchId(branchId)
                .price(BigDecimal.TEN)
                .build();

        ProductPricesRequestDto productPricesRequestDto = new ProductPricesRequestDto(List.of(productId));
        ProductPriceResponseDto productPriceResponseDto = new ProductPriceResponseDto(branchId, productId, BigDecimal.TEN);

        when(productPriceRepository.getByBranchIdAndProductIdIn(branchId, List.of(productId))).thenReturn(List.of(productPrice));
        when(productMapper.convertProductPriceResponseDtoFromProductPrice(productPrice)).thenReturn(productPriceResponseDto);

        List<ProductPriceResponseDto> result = productService.getProductPrices(branchId, productPricesRequestDto);
        assertEquals(List.of(productPriceResponseDto), result);
    }

    @Test
    void shouldGetProductPricesEmpty() {
        UUID productPriceId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();

        ProductPrice productPrice = ProductPrice.builder()
                .priceId(productPriceId)
                .productId(UUID.randomUUID())
                .branchId(branchId)
                .price(BigDecimal.TEN)
                .build();

        ProductPricesRequestDto productPricesRequestDto = new ProductPricesRequestDto(List.of(productId));
        ProductPriceResponseDto productPriceResponseDto = new ProductPriceResponseDto(branchId, productId, BigDecimal.TEN);

        when(productPriceRepository.getByBranchIdAndProductIdIn(branchId, List.of(productId))).thenReturn(List.of(productPrice));
        when(productMapper.convertProductPriceResponseDtoFromProductPrice(productPrice)).thenReturn(productPriceResponseDto);

        List<ProductPriceResponseDto> result = productService.getProductPrices(branchId, productPricesRequestDto);
        assertEquals(List.of(productPriceResponseDto), result);
    }
}
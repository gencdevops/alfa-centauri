package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.request.UpdateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.exception.SupplierNotFoundException;
import com.example.cgrestaurant.mapper.ProductMapper;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.model.enums.ProductStatus;
import com.example.cgrestaurant.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
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
    private ProductMapper productMapper;
    @Mock
    private SupplierService supplierService;

    @Test
    void shouldReturnResponseWhenCreateProduct() {
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        UUID supplierId = UUID.randomUUID();

        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto(productName, defaultPrice, supplierId, ProductStatus.ACTIVE);

        when(productMapper.convertProductFromCreateProductRequestDto(createProductRequestDto)).thenReturn(new Product());
        when(supplierService.getSupplierByID(supplierId)).thenReturn(new Supplier());
        when(productRepository.save(any(Product.class))).thenReturn(new Product());
        when(productMapper.convertProductResponseDtoFromProduct(any(Product.class))).thenReturn(new ProductResponseDto(productName, defaultPrice, ProductStatus.ACTIVE));


        ProductResponseDto product = productService.createProduct(createProductRequestDto);
        assertEquals(productName, product.productName());
        assertEquals(defaultPrice, product.defaultPrice());
        assertEquals(ProductStatus.ACTIVE, product.productStatus());
    }

    @Test
    void shouldThrowExceptionWhenCreateProductAndSupplierIdDoesNotExist() {
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        UUID supplierId = UUID.randomUUID();

        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto(productName, defaultPrice, supplierId, ProductStatus.ACTIVE);

        when(productMapper.convertProductFromCreateProductRequestDto(createProductRequestDto)).thenReturn(new Product());
        when(supplierService.getSupplierByID(supplierId)).thenThrow(new SupplierNotFoundException("Supplier not found."));

        assertThrows(SupplierNotFoundException.class, () -> productService.createProduct(createProductRequestDto));
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        Long id = new Random().nextLong();
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    void shouldReturnProductWhenProductExist() {
        Long id = new Random().nextLong();
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .productId(id)
                .productStatus(productStatus)
                .defaultPrice(defaultPrice)
                .build();
        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));
        when(productMapper.convertProductResponseDtoFromProduct(product)).thenReturn(new ProductResponseDto(productName, defaultPrice, productStatus));

        ProductResponseDto productResponseDto = productService.getProductById(id);
        assertEquals(productName, productResponseDto.productName());
        assertEquals(defaultPrice, productResponseDto.defaultPrice());
        assertEquals(productStatus, productResponseDto.productStatus());
    }

    @Test
    void shouldReturnProductWhenProductNameExist() {
        Long id = new Random().nextLong();
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .productId(id)
                .productStatus(productStatus)
                .defaultPrice(defaultPrice)
                .build();
        when(productRepository.findByProductName(productName)).thenReturn(java.util.Optional.of(product));

        Product actual = productService.getProductByName(productName);
        assertEquals(productName, actual.getProductName());
        assertEquals(defaultPrice, actual.getDefaultPrice());
        assertEquals(productStatus, actual.getProductStatus());
    }

    @Test
    void shouldReturnAllProducts() {
        Long id = new Random().nextLong();
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .productId(id)
                .productStatus(productStatus)
                .defaultPrice(defaultPrice)
                .build();
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.convertProductResponseDtoFromProduct(product)).thenReturn(new ProductResponseDto(productName, defaultPrice, productStatus));

        List<ProductResponseDto> actual = productService.getAllProduct();
        assertEquals(1, actual.size());
    }

    @Test
    void shouldUpdateProduct() {
        Long id = new Random().nextLong();
        String productName = RandomStringUtils.randomAlphabetic(10);
        BigDecimal defaultPrice = BigDecimal.valueOf(10.0);
        ProductStatus productStatus = ProductStatus.ACTIVE;
        Product product = Product.builder()
                .productName(productName)
                .productId(id)
                .productStatus(productStatus)
                .defaultPrice(defaultPrice)
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
        Long id = new Random().nextLong();
        productService.deleteProduct(id);
        verify(productRepository).deleteById(id);
    }

}
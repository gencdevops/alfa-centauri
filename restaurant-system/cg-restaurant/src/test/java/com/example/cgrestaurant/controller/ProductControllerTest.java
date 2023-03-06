package com.example.cgrestaurant.controller;

import com.example.cgcommon.model.ProductStatus;
import com.example.cgrestaurant.BaseIntegrationTest;
import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.request.UpdateProductRequestDto;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends BaseIntegrationTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;

    private Product alreadyExistProduct;

    @BeforeEach
    void setUp() {
        alreadyExistProduct = productRepository.save(
                Product.builder()
                        .productName(RandomStringUtils.random(10))
                        .productStatus(ProductStatus.ACTIVE)
                        .build());
    }



    @Test
    void shouldCreateProduct() throws Exception {
        CreateProductRequestDto createProductRequestDto = new CreateProductRequestDto(
                RandomStringUtils.random(10),
                BigDecimal.ONE,
                UUID.randomUUID(),
                ProductStatus.ACTIVE
        );

        this.mockMvc.perform(post(BASE_PRODUCT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createProductRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value(createProductRequestDto.productName()))
                .andExpect(jsonPath("$.productStatus").value(createProductRequestDto.productStatus().name()));
    }

    @Test
    void shouldReturnProductWhenExist() throws Exception {

       this.mockMvc.perform(get(BASE_PRODUCT_ENDPOINT + "/" + alreadyExistProduct.getId())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value(alreadyExistProduct.getProductName()))
                .andExpect(jsonPath("$.productStatus").value(alreadyExistProduct.getProductStatus().name()));
    }

    @Test
    void shouldReturnNotFoundWhenProductNotExist() throws Exception {
        this.mockMvc.perform(get(BASE_PRODUCT_ENDPOINT + "/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        UpdateProductRequestDto updateProductRequestDto = new UpdateProductRequestDto(
                RandomStringUtils.randomAlphanumeric(10),
                BigDecimal.ONE,
                ProductStatus.PASSIVE
        );

        this.mockMvc.perform(put(BASE_PRODUCT_ENDPOINT + "/" + alreadyExistProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateProductRequestDto)))
                .andExpect(jsonPath("$.productName").value(updateProductRequestDto.productName()));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        UpdateProductRequestDto updateProductRequestDto = new UpdateProductRequestDto(
                RandomStringUtils.randomAlphanumeric(10),
                BigDecimal.ONE,
                ProductStatus.PASSIVE
        );

        this.mockMvc.perform(delete(BASE_PRODUCT_ENDPOINT + "/" + alreadyExistProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
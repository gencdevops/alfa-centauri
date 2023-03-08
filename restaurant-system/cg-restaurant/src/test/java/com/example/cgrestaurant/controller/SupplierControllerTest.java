package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateSupplierRequestDto;
import com.example.cgrestaurant.BaseIntegrationTest;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.SupplierRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class SupplierControllerTest extends BaseIntegrationTest {

    @Autowired
    SupplierController controller;

    @Autowired
    SupplierRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private Supplier alreadyCreatedSupplier;

    @BeforeEach
    void setUp() {
        alreadyCreatedSupplier = repository.save(Supplier.builder()
                .supplierName(RandomStringUtils.randomAlphanumeric(10))
                .build());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void createSupplier() throws Exception {
        CreateSupplierRequestDto request = CreateSupplierRequestDto.builder()
                .supplierName("Supplier 1")
                .build();

        this.mockMvc.perform(post(BASE_SUPPLIER_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.supplierName", Matchers.is(request.supplierName())))
                .andReturn();
    }

    @Test
    void shouldReturnSupplierWhenExists() throws Exception {
        this.mockMvc.perform(get(BASE_SUPPLIER_API_ENDPOINT + "/" + alreadyCreatedSupplier.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.supplierName", Matchers.is(alreadyCreatedSupplier.getSupplierName())))
                .andReturn();
    }

    @Test
    void shouldThrowExceptionWhenSupplierDoesNotExist() throws Exception {
        this.mockMvc.perform(get(BASE_SUPPLIER_API_ENDPOINT + "/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void shouldReturnAllSuppliers() throws Exception {
        this.mockMvc.perform(get(BASE_SUPPLIER_API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    void shouldDeleteSupplier() throws Exception {
        this.mockMvc.perform(delete(BASE_SUPPLIER_API_ENDPOINT + "/" + alreadyCreatedSupplier.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
    }

}

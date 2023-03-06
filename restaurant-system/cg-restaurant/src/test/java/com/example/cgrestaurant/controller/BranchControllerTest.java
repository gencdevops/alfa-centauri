package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateBranchRequestDto;
import com.example.cgrestaurant.dto.request.UpdateBranchRequestDto;
import com.example.cgrestaurant.BaseIntegrationTest;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.repository.BranchRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BranchControllerTest extends BaseIntegrationTest{

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BranchRepository branchRepository;

    private Branch alreadyCreatedBranch;
    @BeforeEach
    void setUp() {
        alreadyCreatedBranch = branchRepository.save(Branch.builder()
                .branchName(RandomStringUtils.randomAlphabetic(10))
                .build());
    }

    @AfterEach
    void tearDown() {
        branchRepository.deleteAll();
    }



    @Test
    void shouldCreateBranch() throws Exception {
        CreateBranchRequestDto request = new CreateBranchRequestDto(
                RandomStringUtils.randomAlphabetic(10), UUID.randomUUID()
        );

        this.mockMvc.perform(post(BASE_BRANCH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branchName", Matchers.is(request.branchName())))
                .andReturn();
    }

    @Test
    void shouldReturnBranchWhenBranchExists() throws Exception {
        CreateBranchRequestDto request = new CreateBranchRequestDto(
                RandomStringUtils.randomAlphabetic(10), UUID.randomUUID()
        );

        this.mockMvc.perform(get(BASE_BRANCH_ENDPOINT + "/" + alreadyCreatedBranch.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branchName", Matchers.is(alreadyCreatedBranch.getBranchName())))
                .andReturn();
    }

    @Test
    void shouldReturn404WhenBranchDoesNotExist() throws Exception {
        CreateBranchRequestDto request = new CreateBranchRequestDto(
                RandomStringUtils.randomAlphabetic(10), UUID.randomUUID()
        );

        this.mockMvc.perform(get(BASE_BRANCH_ENDPOINT + "/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void shouldReturnAllBranch() throws Exception {
        this.mockMvc.perform(get(BASE_BRANCH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andReturn();
    }

    @Test
    void shouldUpdateBranch() throws Exception {
        UpdateBranchRequestDto request = new UpdateBranchRequestDto(
                RandomStringUtils.randomAlphabetic(10)
        );

        this.mockMvc.perform(put(BASE_BRANCH_ENDPOINT + "/" + alreadyCreatedBranch.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.branchName", Matchers.is(request.branchName())))
                .andReturn();
    }

    @Test
    void shouldDeleteBranch() throws Exception {
        this.mockMvc.perform(delete(BASE_BRANCH_ENDPOINT + "/" + alreadyCreatedBranch.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
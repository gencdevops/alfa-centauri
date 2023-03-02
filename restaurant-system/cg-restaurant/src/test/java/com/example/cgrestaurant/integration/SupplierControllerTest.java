package com.example.cgrestaurant.integration;

import com.example.cgrestaurant.controller.SupplierController;
import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SupplierControllerTest extends BaseIntegrationTest{

    @Autowired
    SupplierController controller;

    @Autowired
    SupplierRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createSupplier() throws Exception {
        CreateSupplierRequest request = CreateSupplierRequest.builder()
                .supplierName("Shaya")
                .build();

        var x = this.mockMvc.perform(post(SUPPLIER_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andReturn();

//        CreateExpenseRequest request = ExpenseUtil.generateCreateExpenseRequest(EMPLOYEE_ID);
//        ExpenseDto expected = ExpenseUtil.generateExpenseDto(EXPENSE_ID);
//
//        this.mockMvc.perform(post(EXPENSE_API_ENDPOINT)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", notNullValue()))
//                .andExpect(jsonPath("$.type", is(expected.type())))
//                .andExpect(jsonPath("$.amount", is(expected.amount())))
////                .andExpect(jsonPath("$.receiptDate", is(expenseDto.receiptDate())))
//                .andExpect(jsonPath("$.description", is(expected.description())))
//                .andReturn();

    }

}

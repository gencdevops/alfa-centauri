package com.example.kafkalogbackxmlv2.controller;

import com.example.kafkalogbackxmlv2.client.dto.request.CreateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.CustomerDTO;
import com.example.kafkalogbackxmlv2.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:8080")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create Customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCustomer(@RequestBody CreateCustomerRequest customer) {
        return customerService.createCustomer(customer);
    }

    @Operation(summary = "Get Customer By Id")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(Long id) {
        return customerService.getCustomer(id);
    }

    @Operation(summary = "Update Customer")
    @PutMapping
    @ResponseStatus(HttpStatus.FOUND)
    public String updateCustomer(@RequestBody UpdateCustomerRequest customerRequest, Long id) {
        return customerService.updateCustomer(id, customerRequest);
    }

    @Operation(summary = "Delete Customer")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCustomer(Long id) {
        return customerService.deleteCustomer(id);
    }


}

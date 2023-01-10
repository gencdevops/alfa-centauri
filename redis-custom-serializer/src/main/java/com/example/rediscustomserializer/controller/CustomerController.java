package com.example.rediscustomserializer.controller;



import com.example.rediscustomserializer.dto.request.CreateCustomerRequestDto;
import com.example.rediscustomserializer.dto.request.UpdateCustomerRequest;
import com.example.rediscustomserializer.dto.response.CustomerDto;
import com.example.rediscustomserializer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequestDto customerRequest){
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }


    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerDtoById(id));
    }

    @GetMapping("/customer")
    public ResponseEntity<CustomerDto> getCustomerByName(@RequestParam(name = "customer-name" ) String name){
        return ResponseEntity.ok(customerService.getCustomerDtoByName(name));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,
                                                      @RequestBody UpdateCustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));
    }
}


package com.example.rediscustomserializer.service;


import com.example.rediscustomserializer.dto.request.CreateCustomerRequestDto;
import com.example.rediscustomserializer.dto.request.UpdateCustomerRequest;
import com.example.rediscustomserializer.dto.response.CustomerDto;
import com.example.rediscustomserializer.exception.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {





    public CustomerDto createCustomer(CreateCustomerRequestDto customerRequest){

        return null;
    }

    public List<CustomerDto> getAllCustomers() {
        return null;

    }

    @Transactional
    public CustomerDto getCustomerDtoById(Long id) {
        return null;
    }

    @Transactional
    public CustomerDto getCustomerDtoByName(String name) {
        return null;
    }

    public void deleteCustomer(Long id) {

    }

    public CustomerDto updateCustomer(Long id, UpdateCustomerRequest customerRequest) {
        return null;
    }

  
}
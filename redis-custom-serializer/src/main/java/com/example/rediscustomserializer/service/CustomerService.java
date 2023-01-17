package com.example.rediscustomserializer.service;


import com.example.rediscustomserializer.configuration.RedisConfiguration;
import com.example.rediscustomserializer.dto.request.CreateCustomerRequestDto;
import com.example.rediscustomserializer.dto.request.UpdateCustomerRequest;
import com.example.rediscustomserializer.dto.response.CustomerDto;
import com.example.rediscustomserializer.exception.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomerService {


    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CustomerService(@Qualifier("shRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Cacheable(value = "users", key = "#customerRequest.name", unless = "#result.followers < 12000")
    public CustomerDto createCustomer(CreateCustomerRequestDto customerRequest){
        CustomerDto customerDto = CustomerDto.builder()
                .city(customerRequest.getCity())
                .email(customerRequest.getEmail())
                .name(customerRequest.getName())
                .build();

        redisTemplate.opsForHash().put(customerRequest.getName(), "customer#", customerDto);

        return customerDto;
    }

    public List<CustomerDto> getAllCustomers() {
        return null;

    }


    public CustomerDto getCustomerDtoById(Long id) {
        return null;
    }

    public CustomerDto getCustomerDtoByName(String name) {
        return null;
    }

    public void deleteCustomer(Long id) {

    }


    public CustomerDto updateCustomer(Long id, UpdateCustomerRequest request) {
        return null;
    }

  
}
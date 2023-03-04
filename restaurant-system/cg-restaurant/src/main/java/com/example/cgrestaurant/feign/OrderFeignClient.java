package com.example.cgrestaurant.feign;

import com.example.cgcommon.dto.request.OrderRequestDTO;
import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgrestaurant.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "cg-order")
public interface OrderFeignClient{

    // ##################  TEST  ######################################################
    @GetMapping("/api/dummy")
    ResponseEntity<String> getDummyString();

    // #################################################################################

    @GetMapping
    ResponseEntity<List<OrderResponseDTO>> getAllOrders();

    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO order);



    }

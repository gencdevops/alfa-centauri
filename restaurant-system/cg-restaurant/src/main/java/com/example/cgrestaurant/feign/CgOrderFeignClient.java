package com.example.cgrestaurant.feign;

import com.example.cgrestaurant.dto.feign.OrderRequestDto;
import com.example.cgrestaurant.dto.feign.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cg-order")
public interface CgOrderFeignClient {

    @PostMapping("/api/orders")
    ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto order);
}

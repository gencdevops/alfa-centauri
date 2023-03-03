package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.feign.OrderFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderFeignClient feignClient;

    @GetMapping("/test")
    public ResponseEntity<String> feignTestToOrder() {
        return feignClient.getDummyString();
    }



}

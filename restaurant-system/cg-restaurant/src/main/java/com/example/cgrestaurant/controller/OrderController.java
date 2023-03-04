package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.feign.OrderFeignClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.cgrestaurant.contants.RestaurantConstants.*;

@RequiredArgsConstructor
@Tag(name = "Branch Related APIs")
@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_ORDER)
public class OrderController {

    private final OrderFeignClient feignClient;





}

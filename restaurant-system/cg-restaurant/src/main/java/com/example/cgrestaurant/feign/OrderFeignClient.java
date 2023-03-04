package com.example.cgrestaurant.feign;


import com.example.cgcommon.dto.response.OrderResponseDTO;

import com.example.cgrestaurant.dto.request.PlaceOrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.UUID;

@FeignClient(name = "cg-order")
public interface OrderFeignClient{


    @PostMapping
    ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO);



    }

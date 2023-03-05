package com.example.cgrestaurant.feign;


import com.example.cgcommon.dto.response.OrderResponseDTO;

import com.example.cgrestaurant.dto.request.order.PlaceOrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "cg-order")
public interface OrderFeignClient{


    @PostMapping("/api/v1/orders/place-order")
    OrderResponseDTO placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO);



    }

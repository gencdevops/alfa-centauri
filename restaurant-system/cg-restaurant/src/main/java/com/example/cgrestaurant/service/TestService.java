package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.feign.OrderItemRequestDTO;
import com.example.cgrestaurant.dto.feign.OrderRequestDto;
import com.example.cgrestaurant.feign.CgOrderFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TestService {

    CgOrderFeignClient feignClient;

    public String createOrderFeign() {
        OrderItemRequestDTO orderItemRequestDTO = OrderItemRequestDTO.builder()
                .productId(1L)
                .productName("Latte")
                .quantity(1)
                .totalPrice(BigDecimal.valueOf(70))
                .unitPrice(BigDecimal.valueOf(70))
                .build();

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .orderItems(List.of(orderItemRequestDTO))
                .build();

        var result = feignClient.createOrder(orderRequestDto);
        log.warn(result.toString());
        return result.toString();
    }
}

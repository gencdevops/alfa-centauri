package com.example.cgorder.controller;

import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/api/v1/order")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(orderService.save(orderRequestDto));
    }

    @DeleteMapping
    public void deleteOrder(UUID id){
        orderService.delete(id);
    }
}

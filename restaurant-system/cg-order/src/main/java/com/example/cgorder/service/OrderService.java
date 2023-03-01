package com.example.cgorder.service;

import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;

import java.util.UUID;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto orderRequestDto);

    void delete(UUID id);

    OrderResponseDto update(OrderRequestDto orderRequestDto, UUID id);
}

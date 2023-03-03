package com.example.cgorder.service;

import com.example.cgorder.client.CardDto;
import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(UUID id);

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, CardDto cardDto);

    void deleteOrder(UUID id);

}

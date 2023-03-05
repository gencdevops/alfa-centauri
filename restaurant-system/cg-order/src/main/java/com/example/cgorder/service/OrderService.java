package com.example.cgorder.service;


import com.example.cgorder.dto.OrderResponseDto;
import com.example.cgorder.dto.PlaceOrderRequestDTO;
import jakarta.validation.constraints.NotNull;


public interface OrderService {
    OrderResponseDto placeOrder(@NotNull PlaceOrderRequestDTO placeOrderRequestDTO);

}

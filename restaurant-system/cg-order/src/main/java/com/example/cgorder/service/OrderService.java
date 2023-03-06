package com.example.cgorder.service;


import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import jakarta.validation.constraints.NotNull;


public interface OrderService {
    OrderResponseDTO placeOrder(@NotNull PlaceOrderRequestDTO placeOrderRequestDTO, String idempotentKey);
    String checkIdempotentKey(String idempotentKey);
}

package com.example.cgorder.service;


import com.example.cgorder.dto.OrderResponseDto;
import com.example.cgorder.dto.PlaceOrderRequestDTO;



public interface OrderService {
    OrderResponseDto placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO);

}

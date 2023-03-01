package com.example.cgorder.client;

import com.example.cgorder.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseDto {
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;
}

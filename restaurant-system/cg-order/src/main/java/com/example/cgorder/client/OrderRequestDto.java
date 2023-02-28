package com.example.cgorder.client;

import com.example.cgorder.model.OrderItem;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;
}

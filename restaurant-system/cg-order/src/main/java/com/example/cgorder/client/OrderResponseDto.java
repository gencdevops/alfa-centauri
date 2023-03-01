package com.example.cgorder.client;

import com.example.cgorder.model.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponseDto {
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;
}

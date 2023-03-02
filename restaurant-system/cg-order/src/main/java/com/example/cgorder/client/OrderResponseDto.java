package com.example.cgorder.client;

import com.example.cgorder.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponseDto {
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;
}

package com.example.cgrestaurant.dto.feign;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;
}

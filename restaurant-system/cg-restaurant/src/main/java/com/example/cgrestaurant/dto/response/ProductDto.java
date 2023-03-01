package com.example.cgrestaurant.dto.response;

import com.example.cgrestaurant.model.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record ProductDto(String productName, BigDecimal defaultPrice,
                         @Enumerated(EnumType.STRING) OrderStatus orderStatus) {
}

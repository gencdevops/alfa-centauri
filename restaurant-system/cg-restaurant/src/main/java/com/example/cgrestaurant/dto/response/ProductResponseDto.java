package com.example.cgrestaurant.dto.response;

import com.example.cgrestaurant.model.enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record ProductResponseDto(
        String productName,
        BigDecimal defaultPrice,
        @Enumerated(EnumType.STRING) ProductStatus productStatus) {
}

package com.example.cgrestaurant.dto.request;

import com.example.cgrestaurant.model.enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequestDto(

        @NotNull(message = "Product name cannot be null")
        String productName,
        @PositiveOrZero
        BigDecimal defaultPrice,
        @NotNull
        UUID supplierId,
        @Enumerated(EnumType.STRING) ProductStatus productStatus) {
}

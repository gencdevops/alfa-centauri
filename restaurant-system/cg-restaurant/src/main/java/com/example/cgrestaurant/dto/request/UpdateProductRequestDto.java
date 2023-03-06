package com.example.cgrestaurant.dto.request;

import com.example.cgcommon.model.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        @NotNull(message = "Branch name cannot be null")
        String productName,
        @PositiveOrZero(message = "Must be 0 or greater")
        BigDecimal defaultPrice,
        @Enumerated(EnumType.STRING) ProductStatus productStatus) {
}

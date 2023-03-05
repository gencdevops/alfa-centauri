package com.example.cgrestaurant.dto;

import com.example.cgrestaurant.model.enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductPriceRequestDto(


        @PositiveOrZero
        BigDecimal price
){}

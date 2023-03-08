package com.example.cgrestaurant.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductPriceRequestDto(


        @PositiveOrZero
        BigDecimal price
){}

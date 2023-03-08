package com.example.cgrestaurant.dto.response;

import com.example.cgcommon.model.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;



@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductResponseDto(
        String productName,
        BigDecimal defaultPrice,
        @Enumerated(EnumType.STRING) ProductStatus productStatus) {
}

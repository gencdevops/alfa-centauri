package com.example.cgrestaurant.dto.request;

import com.example.cgrestaurant.model.enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(String productName, BigDecimal defaultPrice, UUID supplierId,
                                   @Enumerated(EnumType.STRING) ProductStatus productStatus) {
}

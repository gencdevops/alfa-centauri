package com.example.cgrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {
    @NotNull(message = "productId field is mandatory")
    private Long productId;

    @NotNull(message = "productName field is mandatory")
    private String productName;

    @NotNull(message = "quantity field is mandatory")
    @PositiveOrZero(message = "quantity field must be positive or zero")
    private Integer quantity;

    @NotNull(message = "unitPrice field is mandatory")
    @PositiveOrZero(message = "unitPrice field must be positive or zero")
    private BigDecimal unitPrice;

    @NotNull(message = "totalPrice field is mandatory")
    @PositiveOrZero(message = "totalPrice field must be positive or zero")
    private BigDecimal totalPrice;
}

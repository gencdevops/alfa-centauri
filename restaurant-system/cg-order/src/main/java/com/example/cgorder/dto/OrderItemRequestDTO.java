package com.example.cgorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {
    @NotNull(message = "productId field is mandatory")
    private UUID productId;

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

package com.example.cgcommon.request;

import com.example.cgcommon.model.ProductStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO implements Serializable {
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

   @NotNull
   private ProductStatus productStatus;

}

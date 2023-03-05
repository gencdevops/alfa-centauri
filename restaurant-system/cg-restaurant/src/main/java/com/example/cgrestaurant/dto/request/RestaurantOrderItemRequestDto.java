package com.example.cgrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantOrderItemRequestDto {

    @NotNull(message = "Product id field is mandatory")
    private UUID productId;

    @NotNull(message = "quantity field is mandatory")
    @PositiveOrZero(message = "quantity field must be positive or zero")
    private Integer quantity;

}

package com.example.cgrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantOrderItemRequestDto {

    @NotNull(message = "productName field is mandatory")
    private String productName;

    @NotNull(message = "quantity field is mandatory")
    @PositiveOrZero(message = "quantity field must be positive or zero")
    private Integer quantity;

}

package com.example.cgrestaurant.dto.request;

import com.example.cgcommon.model.CardInfoDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RestaurantOrderRequestDto(
        List<RestaurantOrderItemRequestDto> restaurantOrderItemRequestDtos,

        @NotNull(message = "Branch Id  cannot be null")
        UUID branchId,
        CardInfoDto cardInfoDto) {
}

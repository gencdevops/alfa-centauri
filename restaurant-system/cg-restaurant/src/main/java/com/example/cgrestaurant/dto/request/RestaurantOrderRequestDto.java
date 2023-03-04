package com.example.cgrestaurant.dto.request;

import com.example.cgcommon.model.CardInfoDto;

import java.util.List;

public record RestaurantOrderRequestDto (List<RestaurantOrderItemRequestDto> restaurantOrderItemRequestDtos,
                                         CardInfoDto cardInfoDto){
}

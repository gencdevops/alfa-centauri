package com.example.cgorder.mapper;

import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequestDto orderRequestDto);

    OrderResponseDto toDto(Order order);
}

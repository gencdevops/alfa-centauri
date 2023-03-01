package com.example.cgorder.mapper;

import com.example.cgorder.client.OrderItemRequestDTO;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequestDTO orderItemRequestDTO);

    OrderResponseDto toDto(OrderItem orderItem);
}

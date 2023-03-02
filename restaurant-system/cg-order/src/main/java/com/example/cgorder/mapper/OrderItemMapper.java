package com.example.cgorder.mapper;

import com.example.cgorder.client.OrderItemRequestDTO;
import com.example.cgorder.client.OrderItemResponseDTO;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequestDTO orderItemRequestDTO);

    OrderItemResponseDTO toDto(OrderItem orderItem);

    List<OrderItem> toEntityList(List<OrderItemRequestDTO> orderItemRequestDTOList);
}

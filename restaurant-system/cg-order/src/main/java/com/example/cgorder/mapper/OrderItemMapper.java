package com.example.cgorder.mapper;

import com.example.cgorder.dto.OrderItemRequestDTO;
import com.example.cgorder.dto.OrderItemResponseDTO;
import com.example.cgorder.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem convertOrderItemFromOrderItemRequestDTO(OrderItemRequestDTO orderItemRequestDTO);

    OrderItemResponseDTO convertOrderItemResponseDTOFromOrderItem(OrderItem orderItem);

    List<OrderItem> comvertOrderItemListFromOrderItemRequestDTOList(List<OrderItemRequestDTO> orderItemRequestDTOList);
}

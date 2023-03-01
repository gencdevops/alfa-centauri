package com.example.cgorder.mapper;

import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Mapping(target = "orderItems", expression = "java(orderItemMapper.toEntityList(orderRequestDto.getOrderItems()))")
    public abstract Order toEntity(OrderRequestDto orderRequestDto);

    public abstract OrderResponseDto toDto(Order order);
}

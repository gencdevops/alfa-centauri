package com.example.cgorder.mapper;


import com.example.cgorder.dto.OrderResponseDto;
import com.example.cgorder.dto.PlaceOrderRequestDTO;
import com.example.cgorder.model.Order;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;

    public abstract Order convertOrderFromPlaceOrderRequestDTO(PlaceOrderRequestDTO orderRequestDto);

    public abstract OrderResponseDto convertPlaceOrderRequestDTOFromOrder(Order order);
}

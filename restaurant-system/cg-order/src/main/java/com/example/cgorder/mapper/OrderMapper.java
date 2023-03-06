package com.example.cgorder.mapper;


import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgorder.model.Order;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;

    public abstract Order convertOrderFromPlaceOrderRequestDTO(PlaceOrderRequestDTO orderRequestDto);

    public abstract OrderResponseDTO convertPlaceOrderRequestDTOFromOrder(Order order);
}

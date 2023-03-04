package com.example.cgrestaurant.service;


import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgrestaurant.dto.request.RestaurantOrderRequestDto;
import com.example.cgrestaurant.dto.request.order.OrderItemRequestDTO;
import com.example.cgrestaurant.dto.request.order.PlaceOrderRequestDTO;
import com.example.cgrestaurant.feign.OrderFeignClient;
import com.example.cgrestaurant.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {
    private final OrderFeignClient orderFeignClient;
    private final ProductService productService;

    public OrderResponseDTO placeOrder(RestaurantOrderRequestDto restaurantOrderRequestDto) {

        List<OrderItemRequestDTO> orderItemRequestDTOS = restaurantOrderRequestDto.restaurantOrderItemRequestDtos().stream().map(restaurantOrderItemRequestDto -> {
            Product productByName = productService.getProductByName(restaurantOrderItemRequestDto.getProductName());

            return OrderItemRequestDTO.builder()
                    .productName(restaurantOrderItemRequestDto.getProductName())
                    .quantity(restaurantOrderItemRequestDto.getQuantity())
                    .unitPrice(productByName.getDefaultPrice())
                    .totalPrice(productByName.getDefaultPrice().multiply(BigDecimal.valueOf(restaurantOrderItemRequestDto.getQuantity())))
                    .build();
        }).toList();

        BigDecimal totalPrice = orderItemRequestDTOS.stream()
                .map(OrderItemRequestDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //  Product  --> order items
        PlaceOrderRequestDTO dto = PlaceOrderRequestDTO.builder()
                .orderItems(orderItemRequestDTOS)
                .totalPrice(totalPrice)
                .cardInfo(restaurantOrderRequestDto.cardInfoDto())
                .build();
        return orderFeignClient.placeOrder(dto);
    }

//    public void OrderO()
}

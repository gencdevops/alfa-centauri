package com.example.cgrestaurant.service;


import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.request.OrderItemRequestDTO;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgrestaurant.dto.request.RestaurantOrderItemRequestDto;
import com.example.cgrestaurant.dto.request.RestaurantOrderRequestDto;
import com.example.cgrestaurant.feign.OrderFeignClient;
import com.example.cgrestaurant.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {
    private final OrderFeignClient orderFeignClient;
    private final ProductService productService;



    public OrderResponseDTO placeOrder(RestaurantOrderRequestDto restaurantOrderRequestDto) {
        List<OrderItemRequestDTO> orderItemRequestDTOS = restaurantOrderRequestDto.restaurantOrderItemRequestDtos().stream()
                .map(item -> fillOrderItemRequestDtoWithProductInfo(item, restaurantOrderRequestDto.branchId()))
                .toList();

        BigDecimal totalPrice = orderItemRequestDTOS.stream()
                .map(OrderItemRequestDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String idempotentKey = orderFeignClient.createIdempotentKey(restaurantOrderRequestDto.idempotentKey());


        PlaceOrderRequestDTO placeOrderRequestDTO = PlaceOrderRequestDTO.builder()
                .orderItems(orderItemRequestDTOS)
                .totalPrice(totalPrice)
                .branchId(restaurantOrderRequestDto.branchId())
                .cardInfo(restaurantOrderRequestDto.cardInfoDto())
                .build();
        return orderFeignClient.placeOrder(idempotentKey,placeOrderRequestDTO);
    }


    private OrderItemRequestDTO fillOrderItemRequestDtoWithProductInfo(RestaurantOrderItemRequestDto restaurantOrderItemRequestDto,
                                                                       UUID branchID) {
        Product product = productService.findByProductId(restaurantOrderItemRequestDto.getProductId());
        ProductPriceResponseDto productPrice = productService.getProductPrice(product.getId(), branchID);


        return OrderItemRequestDTO.builder()
                .quantity(restaurantOrderItemRequestDto.getQuantity())
                .unitPrice(productPrice.price())
                .productId(product.getId())
                .totalPrice(productPrice.price()
                        .multiply(BigDecimal.valueOf(restaurantOrderItemRequestDto.getQuantity())))
                .build();

    }


}


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

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {
    private final OrderFeignClient orderFeignClient;
    private final ProductService productService;

    public OrderResponseDTO placeOrder(RestaurantOrderRequestDto restaurantOrderRequestDto) {

        List<Product> productList = restaurantOrderRequestDto.restaurantOrderItemRequestDtos().stream()
                .map(product -> productService.getProductByName(product.getProductName())).toList();


        OrderItemRequestDTO orderItemRequestDTO = OrderItemRequestDTO




        return null;
    }

    public void OrderO()
}

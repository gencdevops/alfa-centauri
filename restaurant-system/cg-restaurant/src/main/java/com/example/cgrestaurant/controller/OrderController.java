package com.example.cgrestaurant.controller;

import com.example.cgcommon.dto.response.OrderResponseDTO;

import com.example.cgrestaurant.dto.request.RestaurantOrderRequestDto;
import com.example.cgrestaurant.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.cgrestaurant.contants.RestaurantConstants.*;

@RequiredArgsConstructor
@Tag(name = "Order Related APIs")
@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_ORDER)
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Order Operations")
    @PostMapping("/place-order")
    @ResponseStatus(value = HttpStatus.OK)
    public OrderResponseDTO placeOrder(@Valid @RequestBody RestaurantOrderRequestDto restaurantOrderRequestDto) {
        return orderService.placeOrder(restaurantOrderRequestDto);
    }

}

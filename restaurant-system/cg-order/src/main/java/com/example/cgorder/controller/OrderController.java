package com.example.cgorder.controller;


import com.example.cgorder.dto.OrderResponseDto;
import com.example.cgorder.dto.PlaceOrderRequestDTO;
import com.example.cgorder.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.example.cgorder.constants.OrderConstants.*;

@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_ORDER)
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody @Valid PlaceOrderRequestDTO placeOrderRequestDTO) {

        return ResponseEntity.ok(orderService.placeOrder(placeOrderRequestDTO));
    }

}


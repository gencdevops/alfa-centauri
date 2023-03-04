package com.example.cgorder.controller;

import com.example.cgorder.client.CardDto;
import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.exception.OrderNotFoundException;
import com.example.cgorder.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get all orders")
    @ApiResponses(value =
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders in system",
                    content = @Content(
                            schema = @Schema(implementation = OrderResponseDto[].class),
                            mediaType = "application/json")))
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get an order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order exists in system",
                    content = @Content(
                            schema = @Schema(implementation = OrderResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "433",
                    description = "Order not found",
                    content = @Content(
                            schema = @Schema(implementation = OrderNotFoundException.class),
                            mediaType = "application/json"))})
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Create order")
    @ApiResponses(value =
            @ApiResponse(
                    responseCode = "201",
                    description = "Order created",
                    content = @Content(
                            schema = @Schema(implementation = OrderResponseDto.class),
                            mediaType = "application/json")))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto order, CardDto cardDto) {
        OrderResponseDto createdOrder = orderService.createOrder(order, cardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @Operation(summary = "Delete an order")
    @ApiResponses(value =
            @ApiResponse(
                    responseCode = "204",
                    description = "Order deleted",
                    content = @Content(
                            schema = @Schema(implementation = OrderResponseDto.class),
                            mediaType = "application/json")))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}


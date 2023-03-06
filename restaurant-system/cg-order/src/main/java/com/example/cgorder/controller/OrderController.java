package com.example.cgorder.controller;


import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgorder.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.cgorder.constants.OrderConstants.*;

@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_ORDER)
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;


    @Operation(summary = "Create order")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "201",
            description = "Place order",
            content = @Content(
                    schema = @Schema(implementation = OrderResponseDTO.class),
                    mediaType = "application/json")))
    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO placeOrder(
            @RequestParam String idempotentKey,
            @RequestBody @Valid PlaceOrderRequestDTO placeOrderRequestDTO) {

        return orderService.placeOrder(placeOrderRequestDTO, idempotentKey);
    }

    @Operation(summary = "Create idempotent key")
    @GetMapping("/idempotent")
    @ResponseStatus(HttpStatus.CREATED)
    public String createIdempotentKey(@RequestParam(required = false) String idempotentKey) {

        return orderService.checkIdempotentKey(idempotentKey);
    }


}


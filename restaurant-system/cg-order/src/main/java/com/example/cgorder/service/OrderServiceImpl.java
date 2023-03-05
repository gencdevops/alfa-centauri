package com.example.cgorder.service;


import com.example.cgorder.dto.OrderResponseDto;
import com.example.cgorder.dto.PlaceOrderRequestDTO;
import com.example.cgorder.exception.OrderNotFoundException;
import com.example.cgorder.exception.OrderPayloadDeserializeException;
import com.example.cgorder.mapper.OrderItemMapper;
import com.example.cgorder.mapper.OrderMapper;
import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final ObjectMapper objectMapper;
    private final ProducerService producerService;

    private final OrderOutBoxService orderOutBoxService;

    @Override
    @Transactional //TODO :  burada enum belirtmeye gerek var mi
    public OrderResponseDto placeOrder(@NotNull PlaceOrderRequestDTO placeOrderRequestDTO) {
        var order = orderMapper.convertOrderFromPlaceOrderRequestDTO(placeOrderRequestDTO);

        var orderItemList = placeOrderRequestDTO.getOrderItems().stream().map(orderItemMapper::convertOrderItemFromOrderItemRequestDTO).toList();

        order.setOrderItems(orderItemList);
        order.setOrderStatus(OrderStatus.RECEIVED);


        OrderOutbox orderOutbox;
        try {
            orderOutbox = OrderOutbox.builder()
                    .orderPayload(objectMapper.writeValueAsString(order))
                    .orderOutboxId(order.getOrderId())
                    .transactionId("12345667899032")
                    .build();
            orderOutBoxService.saveOrderOutbox(orderOutbox);
        } catch (JsonProcessingException e) {
            // TODO : burada exception handlerda yakalayalim mi? mappera alinca MapStruct icerisinde kontrol et
            throw new OrderPayloadDeserializeException(e.getMessage());
        }

        producerService.sendMessage(order);


        orderOutBoxService.deleteOrderOutbox(orderOutbox.getOrderOutboxId());
        return orderMapper.convertPlaceOrderRequestDTOFromOrder(orderRepository.save(order));
    }



}

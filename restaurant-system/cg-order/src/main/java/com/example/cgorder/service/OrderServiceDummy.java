package com.example.cgorder.service;


import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderItem;
import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderOutboxRepository;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceDummy {
    private final OrderOutboxRepository orderOutboxRepository;

    private final OrderRepository orderRepository;

    private final ProducerService producerService;
    private final ObjectMapper objectMapper;

    public void createAndSuccessPayment() throws JsonProcessingException {



        Order orderModel = Order.
                builder()
                .orderId(UUID.randomUUID())
                .orderStatus(OrderStatus.RECEIVED)
                .build();


        OrderItem orderItem  = OrderItem.builder()
                .orderItemId(UUID.randomUUID())
                .productId(2L)
                .quantity(4)
                .productName("latte")
                .totalPrice(BigDecimal.TEN)
                .unitPrice(BigDecimal.ONE)
                .build();

        OrderItem orderItem2  = OrderItem.builder()
                .orderItemId(UUID.randomUUID())
                .productId(2L)
                .quantity(4)
                .productName("latte")
                .totalPrice(BigDecimal.TEN)
                .unitPrice(BigDecimal.ONE)
                .build();

        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        orderItemArrayList.add(orderItem);
        orderItemArrayList.add(orderItem2);

        orderModel.setOrderItems(orderItemArrayList);


        orderRepository.save(orderModel);



        OrderOutbox orderOutbox = OrderOutbox.builder()
                .orderPayload(objectMapper.writeValueAsString(orderModel))
                .transactionId("12345667899032")
                .build();

        orderOutboxRepository.saveAndFlush(orderOutbox);


        producerService.sendMessage(orderModel);


        orderOutboxRepository.deleteById(orderOutbox.getOrderOutboxId());








    }
}

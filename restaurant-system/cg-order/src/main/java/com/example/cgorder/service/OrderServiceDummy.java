package com.example.cgorder.service;


import com.example.cgorder.repository.OrderOutboxRepository;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceDummy {
    private final OrderOutboxRepository orderOutboxRepository;

    private final OrderRepository orderRepository;

    private final ProducerService producerService;





    private final ObjectMapper objectMapper;

    public void createAndSuccessPayment() throws JsonProcessingException {
        OrderModel orderModel = OrderModel.
                builder()
                .id(UUID.randomUUID())
                .orderStatus(OrderStatus.RECIEVED)
                .name("latte")
                .build();

        orderRepository.save(orderModel);


        UUID id = UUID.randomUUID();
        OrderOutbox orderOutbox = OrderOutbox.builder()
                .orderPayload(objectMapper.writeValueAsString(orderModel))
                .id(id)
                .transactionId("12345667899032")
                .build();

        orderOutboxRepository.save(orderOutbox);
        // TODO : EVENT BASILCAK

        producerService.sendMessage(order);
        // TODO : ORDEREVENT NESNESI GODNERILECEK

        orderOutboxRepository.deleteById(id);








    }
}

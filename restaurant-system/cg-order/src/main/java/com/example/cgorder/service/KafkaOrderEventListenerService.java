package com.example.cgorder.service;


import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderEventListenerService {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}" , groupId = "${spring.kafka.group.id}")
    public void listen(String order) {
        try {
            Order order1 = objectMapper.readValue(order, Order.class);
            log.info(")");
            Optional<Order> orderOptional = orderRepository.findById(order1.getOrderId());
            orderOptional.ifPresent(this::updateOrderFromOrderOutboxRetry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateOrderFromOrderOutboxRetry(Order order) {
        order.setOrderStatus(OrderStatus.PREPARING);
        orderRepository.save(order);
    }
}

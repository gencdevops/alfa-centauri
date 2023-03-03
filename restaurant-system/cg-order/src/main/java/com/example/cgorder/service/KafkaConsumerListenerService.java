package com.example.cgorder.service;


import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerListenerService {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}" , groupId = "${spring.kafka.group.id}")
    public void listen(OrderO order) {
        System.out.println("islem basariyla gerceklesti " + orderModel.getName() + orderModel.getOrderStatus());
        Optional<Order> orderModel1 = orderRepository.findById(orderModel.getId());
        orderModel1.get().setOrderStatus(OrderStatus.PREPARING);
        orderRepository.save(orderModel);

    }
}

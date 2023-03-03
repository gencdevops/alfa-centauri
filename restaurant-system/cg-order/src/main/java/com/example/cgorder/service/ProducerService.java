package com.example.cgorder.service;

import com.example.cgorder.model.Order;
import com.example.orderdemo.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topic;



    public void sendMessage( Order order) {
        kafkaTemplate.send(topic,  order);
    }



}

package com.example.cgorder.service;

import com.example.cgorder.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private  String producerTopic;



    public void sendMessage(Order order) {
        log.info("Order sendMessage()   {}", order);
        //TODO :  buraya retry mekanizmasi kurulacak
        kafkaTemplate.send(producerTopic,  order);
    }



}

package com.example.kafkaui.messaging;

import com.example.kafkaui.dto.HelloRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@EnableKafka
@Component
public class HelloConsumer {

    @KafkaListener(topics = "turkcell.message.taner",  containerFactory = "kafkaListenerContainerFactory", groupId = "1")
    public void listener(@Payload HelloRequest request) {
        log.info("consume -> " + request);
    }
}

package com.example.kafkaui.messaging;

import com.example.kafkaui.dto.HelloRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
@RequiredArgsConstructor
@Slf4j
public class HelloProducer {

    private static int MESSAGE_COUNT = 0;

    private final KafkaTemplate<String, HelloRequest> kafkaTemplate;

    public void produce(HelloRequest request) {
        request.setMessageCount(++MESSAGE_COUNT);
        kafkaTemplate.send("turkcell.message.taner", request);
        log.info("produce -> " + request);
    }
}

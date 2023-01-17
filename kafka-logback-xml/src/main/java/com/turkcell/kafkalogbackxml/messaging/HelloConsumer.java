package com.turkcell.kafkalogbackxml.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@EnableKafka
@Component
public class HelloConsumer {

    @KafkaListener(topics = "turkcell.message.logback", groupId = "1")
    public void listener(String logMessage) {
        log.info("Info mesaj alındı." + logMessage);
    }
}

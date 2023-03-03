package com.example.cgorder.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaListenerConfig {
    private final ConsumerFactory<String, Object> consumerFactory;




    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> getConcurrentKafkaListener() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}

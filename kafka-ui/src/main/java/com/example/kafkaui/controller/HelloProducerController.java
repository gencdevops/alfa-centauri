package com.example.kafkaui.controller;

import com.example.kafkaui.dto.HelloRequest;
import com.example.kafkaui.messaging.HelloProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hello")
public class HelloProducerController {

    private final HelloProducer helloProducer;

    @GetMapping
    public ResponseEntity<HelloRequest> hello(@RequestBody HelloRequest helloRequest) {

        helloProducer.produce(helloRequest);
        return ResponseEntity.ok(helloRequest);
    }
}

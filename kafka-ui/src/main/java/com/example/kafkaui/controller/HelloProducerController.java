package com.example.kafkaui.controller;

import com.example.kafkaui.dto.HelloRequest;
import com.example.kafkaui.messaging.HelloProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hello")
public class HelloProducerController {

    private final HelloProducer helloProducer;

    @PostMapping
    public ResponseEntity<HelloRequest> hello(@RequestBody HelloRequest helloRequest) {

        helloProducer.produce(helloRequest);
        return ResponseEntity.ok(helloRequest);
    }
}

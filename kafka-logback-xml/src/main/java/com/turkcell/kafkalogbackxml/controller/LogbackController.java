package com.turkcell.kafkalogbackxml.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/logback")
public class LogbackController {

    @GetMapping
    public ResponseEntity<String> logbas() {
        log.info("log bastım.");
        log.warn("log bastım2.");
        log.error("log bastım3.");

        return ResponseEntity.ok("log basıldı.");
    }

}

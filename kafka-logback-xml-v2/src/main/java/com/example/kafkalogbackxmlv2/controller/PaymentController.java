package com.example.kafkalogbackxmlv2.controller;

import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequestWithoutSender;
import com.example.kafkalogbackxmlv2.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "localhost:8080")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/transfer")
    public String transfer(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.transfer(paymentRequest);
    }

    @PostMapping("/deposit")
    public String deposit(@RequestBody PaymentRequestWithoutSender paymentRequest) {
        return paymentService.deposit(paymentRequest);
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody PaymentRequestWithoutSender paymentRequest) {
        return paymentService.withdraw(paymentRequest);
    }
}

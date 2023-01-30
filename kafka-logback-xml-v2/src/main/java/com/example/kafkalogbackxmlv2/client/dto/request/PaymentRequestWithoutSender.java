package com.example.kafkalogbackxmlv2.client.dto.request;

import com.example.kafkalogbackxmlv2.model.enums.Currency;
import lombok.Builder;

@Builder
public record PaymentRequestWithoutSender(Long receiverId, double amount, Currency currency) {
}

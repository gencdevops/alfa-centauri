package com.example.kafkalogbackxmlv2.client.dto.response;

import com.example.kafkalogbackxmlv2.model.enums.Currency;

import java.math.BigDecimal;

public record AccountDTO(Long id, BigDecimal balance, Currency currency) {
}


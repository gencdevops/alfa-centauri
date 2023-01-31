package com.example.kafkalogbackxmlv2.client.dto.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateCustomerRequest(String firstname, String lastname, LocalDate birthdate) {
}

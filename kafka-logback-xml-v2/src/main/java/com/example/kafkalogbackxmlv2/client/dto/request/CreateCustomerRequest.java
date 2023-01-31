package com.example.kafkalogbackxmlv2.client.dto.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateCustomerRequest(String firstname, String lastname, LocalDate birthdate, String email)  {
}

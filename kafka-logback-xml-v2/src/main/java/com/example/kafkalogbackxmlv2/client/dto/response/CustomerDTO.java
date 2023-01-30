package com.example.kafkalogbackxmlv2.client.dto.response;

import java.time.LocalDate;

public record CustomerDTO(Long id, String firstname, String lastname, LocalDate birthdate, String email) {
}

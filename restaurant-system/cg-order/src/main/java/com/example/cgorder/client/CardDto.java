package com.example.cgorder.client;

public record CardDto(
    String holderName,

    String cardNumber,

    Short expireYear,

    Short expireMonth,

    String cvc
){}
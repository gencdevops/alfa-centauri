package com.example.cgcommon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardInfoDto {
    private String holderName;

    private String cardNumber;

    private Short expireYear;

    private Short expireMonth;

    private String cvc;
}
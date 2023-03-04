package com.example.cgorder.client;

import com.example.cgorder.validation.annotation.ExpireYear;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record CardDto(
        @NotBlank(message = "Kart sahibi adı boş olamaz.")
        String holderName,

        @CreditCardNumber(message = "Geçersiz kart numarası.")
        String cardNumber,

        @ExpireYear(message = "Geçersiz son kullanma yılıb")
        String expireYear,

        @Pattern(regexp = "^(0?[1-9]|1[0-2])$", message = "Geçersiz son kullanma ayı.")
        String expireMonth,

        @Size(min = 3, max = 4, message = "Geçersiz CVC.")
        String cvc
) {
}
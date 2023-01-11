package com.example.customvalidation.dto.request;

import com.example.customvalidation.validation.annotations.City;
import com.example.customvalidation.validation.annotations.TCKN;

import javax.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank
        String name,

        @TCKN
        String tckn,

        @City
        String city
) {
}

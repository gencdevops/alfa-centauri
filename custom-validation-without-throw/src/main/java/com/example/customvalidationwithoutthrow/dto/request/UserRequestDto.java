package com.example.customvalidationwithoutthrow.dto.request;

import com.example.customvalidationwithoutthrow.validation.annotations.TCKN;

import javax.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank
        String name,

        @TCKN
        String tckn
) {
}

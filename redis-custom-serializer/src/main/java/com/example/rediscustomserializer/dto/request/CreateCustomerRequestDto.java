package com.example.rediscustomserializer.dto.request;


import com.example.rediscustomserializer.dto.response.CityDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@Data
public class CreateCustomerRequestDto {
    @NotBlank(message = "Name cannot empty")
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private CityDto city;

    @Email(regexp = "^(.+)@(.+)$", message = "Email is not valid. Please follow the example: turkcell@mail.com")
    @NotBlank(message = "Email must not be null")
    private String email;

}

package com.example.rediscustomserializer.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerDto {


    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty(value = "birth-date")
    private LocalDate birthDate;
    private CityDto city;
    private String email;

}

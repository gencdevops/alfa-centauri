package com.example.rediscustomserializer.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class CustomerDto {


    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty(value = "birth-date")
    private LocalDate birthDate;
    private CityDto city;
    private String email;

}

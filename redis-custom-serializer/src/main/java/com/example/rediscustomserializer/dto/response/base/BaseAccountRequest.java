package com.example.rediscustomserializer.dto.response.base;



import com.example.rediscustomserializer.model.enums.City;
import com.example.rediscustomserializer.model.enums.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class BaseAccountRequest {

    @NotNull(message = "Customer id must not be null")
    private Long customerId;

    @NotNull
    @Min(value = 0, message = "Balance should not be less than 0")
    private Double balance;

    @NotNull(message = "Currency must not be null")
    private Currency currency;

    @NotNull(message = "City must not be null")
    private City city;
}

package com.example.rediscustomserializer.dto.response;



import com.example.rediscustomserializer.model.enums.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Builder
public class AccountDto {


    private long id;

    private Long customerId;
    private Double balance;
    private Currency currency;
}
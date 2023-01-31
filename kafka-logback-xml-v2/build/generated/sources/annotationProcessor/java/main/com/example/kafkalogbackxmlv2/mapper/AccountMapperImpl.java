package com.example.kafkalogbackxmlv2.mapper;

import com.example.kafkalogbackxmlv2.client.dto.request.CreateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.AccountDTO;
import com.example.kafkalogbackxmlv2.model.entity.Account;
import com.example.kafkalogbackxmlv2.model.enums.Currency;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T09:27:28+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccountFromCreateAccountRequest(CreateAccountRequest accountRequest) {
        if ( accountRequest == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.currency( accountRequest.currency() );

        return account.build();
    }

    @Override
    public Account toAccountFromUpdateAccountRequest(Account account, UpdateAccountRequest accountRequest) {
        if ( accountRequest == null ) {
            return account;
        }

        account.setCurrency( accountRequest.currency() );

        return account;
    }

    @Override
    public AccountDTO toAccountDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        Long id = null;
        BigDecimal balance = null;
        Currency currency = null;

        id = account.getId();
        balance = account.getBalance();
        currency = account.getCurrency();

        AccountDTO accountDTO = new AccountDTO( id, balance, currency );

        return accountDTO;
    }
}

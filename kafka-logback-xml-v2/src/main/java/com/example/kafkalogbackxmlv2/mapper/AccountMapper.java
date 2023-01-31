package com.example.kafkalogbackxmlv2.mapper;

import com.example.kafkalogbackxmlv2.client.dto.request.CreateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.AccountDTO;
import com.example.kafkalogbackxmlv2.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(implementationName = "AccountMapperImpl", componentModel = "spring")
public interface AccountMapper {

    @Mapping(target ="id", ignore = true)
    Account toAccountFromCreateAccountRequest(CreateAccountRequest accountRequest);

    @Mapping(target ="id", ignore = true)
    Account toAccountFromUpdateAccountRequest(@MappingTarget Account account, UpdateAccountRequest accountRequest);

    @Mapping(target = ".", source = "id")
    AccountDTO toAccountDTO(Account account);
}

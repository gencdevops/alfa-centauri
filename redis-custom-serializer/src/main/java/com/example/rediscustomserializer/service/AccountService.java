package com.example.rediscustomserializer.service;


import com.example.rediscustomserializer.dto.request.CreateAccountRequest;
import com.example.rediscustomserializer.dto.request.UpdateAccountRequest;
import com.example.rediscustomserializer.dto.response.AccountDto;
import com.example.rediscustomserializer.exception.AccountNotFoundException;
import com.example.rediscustomserializer.exception.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountService {



    private final CustomerService customerService;




    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {




        return null;
    }


    public AccountDto updateAccount(long id, UpdateAccountRequest request) {
        return null;
    }


    public List<AccountDto> getAllAccountsDto() {
     return null;
    }

    public AccountDto getAccountById(Long id) {
        return null;
    }


    public void deleteAccount(Long id) {

    }

    public AccountDto withdrawMoney(Long id, Double amount) {

        return null;
    }


    public AccountDto addMoney(Long id, Double amount) {
        return null;
    }



}
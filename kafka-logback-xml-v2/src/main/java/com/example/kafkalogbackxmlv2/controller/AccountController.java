package com.example.kafkalogbackxmlv2.controller;

import com.example.kafkalogbackxmlv2.client.dto.request.CreateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.AccountDTO;
import com.example.kafkalogbackxmlv2.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@CrossOrigin(origins = "localhost:8080")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create Account")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createAccount(@RequestBody CreateAccountRequest accountRequest, Long customerId) {
        return accountService.createAccount(accountRequest, customerId);
    }

    @Operation(summary = "Get Account By Id")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAccount(Long id) {
        return accountService.getAccount(id);
    }

    @Operation(summary = "Update Account")
    @PutMapping
    @ResponseStatus(HttpStatus.FOUND)
    public String updateAccount(@RequestBody UpdateAccountRequest accountRequest, Long id) {
        return accountService.updateAccount(id, accountRequest);
    }

    @Operation(summary = "Delete Account")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteAccount(Long id) {
        return accountService.deleteAccount(id);
    }

}

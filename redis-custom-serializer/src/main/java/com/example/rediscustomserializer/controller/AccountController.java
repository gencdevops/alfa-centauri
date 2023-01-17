package com.example.rediscustomserializer.controller;


import com.example.rediscustomserializer.dto.request.CreateAccountRequest;
import com.example.rediscustomserializer.dto.request.UpdateAccountRequest;
import com.example.rediscustomserializer.dto.response.AccountDto;
import com.example.rediscustomserializer.service.AccountService;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccountsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(
            @Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable long id, @RequestBody UpdateAccountRequest updateAccountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(id, updateAccountRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDto> withdrawMoney(@PathVariable Long id, @PathVariable Double amount) {
        return ResponseEntity.ok(accountService.withdrawMoney(id, amount));
    }

    @PutMapping("/add/{id}/{amount}")
    public ResponseEntity<AccountDto> addMoney(@PathVariable Long id, @PathVariable Double amount) {
        return ResponseEntity.ok(accountService.addMoney(id, amount));
    }



}

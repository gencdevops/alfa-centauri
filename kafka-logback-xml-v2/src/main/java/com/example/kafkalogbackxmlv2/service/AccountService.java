package com.example.kafkalogbackxmlv2.service;


import com.example.kafkalogbackxmlv2.client.dto.request.CreateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateAccountRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.AccountDTO;
import com.example.kafkalogbackxmlv2.exception.GeneralException;
import com.example.kafkalogbackxmlv2.mapper.AccountMapper;
import com.example.kafkalogbackxmlv2.model.entity.Account;
import com.example.kafkalogbackxmlv2.repository.AccountRepository;
import com.example.kafkalogbackxmlv2.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    public String createAccount(CreateAccountRequest accountRequest, Long customerId) {
        Account account = accountMapper.toAccountFromCreateAccountRequest(accountRequest);
        account.setCustomer(customerRepository.findById(customerId).get());
        this.accountRepository.save(account);
        return "Hesap başarıyla oluşturuldu.";
    }

    public AccountDTO getAccount(Long id) {
        return  this.accountRepository.findById(id)
                .map(accountMapper::toAccountDTO)
                .orElseThrow(() -> new GeneralException("Hesap bulunamadı."));
    }
    public String updateAccount(Long id, UpdateAccountRequest accountRequest) {
        Account updatedAccount = accountRepository.findById(id)
                .map(account -> accountMapper.toAccountFromUpdateAccountRequest(account, accountRequest))
                .orElseThrow(() -> new GeneralException("Hesap güncellenemedi."));
        accountRepository.save(updatedAccount);
        return "Hesap başarıyla güncellendi.";
    }
    public String deleteAccount(Long id) {
        boolean isAccountExist = accountRepository.existsById(id);
        if (isAccountExist) {
            accountRepository.deleteById(id);
            return "Hesap başarıyla silindi.";
        }
        throw new GeneralException("Silinecek hesap bulunamadı.");
    }
}

package com.example.kafkalogbackxmlv2.service;

import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequestWithoutSender;
import com.example.kafkalogbackxmlv2.exception.GeneralException;
import com.example.kafkalogbackxmlv2.mapper.PaymentMapper;
import com.example.kafkalogbackxmlv2.model.entity.Account;
import com.example.kafkalogbackxmlv2.model.entity.Payment;
import com.example.kafkalogbackxmlv2.model.enums.Currency;
import com.example.kafkalogbackxmlv2.repository.AccountRepository;
import com.example.kafkalogbackxmlv2.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public String transfer(PaymentRequest paymentRequest) {
        Optional<Account> optionalSender = accountRepository.findById(paymentRequest.senderId());
        Optional<Account> optionalReceiver = accountRepository.findById(paymentRequest.receiverId());

        optionalSender.ifPresentOrElse(sender -> {
                    isBalanceEnough(sender, paymentRequest.amount());
                    checkCurrencyMatch(sender, paymentRequest.currency());
                    sender.updateBalance(-1 * paymentRequest.amount());
                    accountRepository.save(sender);
                },
                () -> {
                    throw new GeneralException("Gönderen hesabı bulunamadı." + paymentRequest);
                }
        );
        optionalReceiver.ifPresentOrElse(receiver -> {
                    checkCurrencyMatch(receiver, paymentRequest.currency());
                    receiver.updateBalance(paymentRequest.amount());
                    accountRepository.save(receiver);
                },
                () -> {
                    throw new GeneralException("Alıcı hesabı bulunamadı." + paymentRequest);
                });

        Payment payment = paymentMapper.toPaymentFromPaymentRequest(paymentRequest);
        paymentRepository.save(payment);
        log.info(payment.toString());
        return "Transfer başarılı.";
    }


    private void isBalanceEnough(Account account, double amount) {
        if (account.getBalance().doubleValue() < amount)
            throw new GeneralException("Bakiye yetersiz.");
    }

    private Account checkCurrencyMatch(Account account, Currency currency) {
        if (account.getCurrency() != currency)
            throw new GeneralException("Eşleşmeyen para birimi.");
        return account;
    }

    public String deposit(PaymentRequestWithoutSender paymentRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(paymentRequest.receiverId());

        optionalAccount.ifPresentOrElse(account -> {
                    checkCurrencyMatch(account, paymentRequest.currency());
                    account.updateBalance(paymentRequest.amount());
                    accountRepository.save(account);
                },
                () -> {
                    throw new GeneralException("Hesap bulunamadı." + paymentRequest);
                });

        Payment payment = paymentMapper.toPaymentFromPaymentRequestWithoutSender(paymentRequest);
        paymentRepository.save(payment);
        log.info(payment.toString());
        return "Hesabınıza para başarıyla yatırılmıştır.";
    }

    public String withdraw(PaymentRequestWithoutSender paymentRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(paymentRequest.receiverId());

        optionalAccount.ifPresentOrElse(account -> {
                    isBalanceEnough(account, paymentRequest.amount());
                    checkCurrencyMatch(account, paymentRequest.currency());
                    account.updateBalance(-1 * paymentRequest.amount());
                    accountRepository.save(account);
                },
                () -> {
                    throw new GeneralException("Hesap bulunamadı." + paymentRequest);
                });


        Payment payment = paymentMapper.toPaymentFromPaymentRequestWithoutSender(paymentRequest);
        paymentRepository.save(payment);
        log.info(payment.toString());
        return "Hesabınıza para başarıyla çekilmiştir.";
    }
}

package com.example.cgorder;

import io.craftgate.Craftgate;
import io.craftgate.model.Currency;
import io.craftgate.model.PaymentGroup;
import io.craftgate.model.PaymentPhase;
import io.craftgate.request.CreatePaymentRequest;
import io.craftgate.request.dto.Card;
import io.craftgate.request.dto.PaymentItem;
import io.craftgate.response.PaymentResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CgOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CgOrderApplication.class, args);
    }

    @Bean
    public CommandLineRunner connectCraftgate(ConfigurableApplicationContext applicationContext){
        return (args) -> {
            Craftgate craftgate = new Craftgate("sandbox-MkOuIWzmHFxsIKCaDzSVsARStdmJqlrc", "sandbox-JsgPbznSePymfcSiDHelyQzMknDqAYNJ", "https://sandbox-api.craftgate.io");

            List<PaymentItem> items = new ArrayList<>();

            items.add(PaymentItem.builder()
                    .name("item 1")
                    .externalId(UUID.randomUUID().toString())
                    .price(BigDecimal.valueOf(30))
                    .build());

            items.add(PaymentItem.builder()
                    .name("item 2")
                    .externalId(UUID.randomUUID().toString())
                    .price(BigDecimal.valueOf(50))
                    .build());

            items.add(PaymentItem.builder()
                    .name("item 3")
                    .externalId(UUID.randomUUID().toString())
                    .price(BigDecimal.valueOf(20))
                    .build());

            CreatePaymentRequest request = CreatePaymentRequest.builder()
                    .price(BigDecimal.valueOf(100))
                    .paidPrice(BigDecimal.valueOf(100))
                    .walletPrice(BigDecimal.ZERO)
                    .installment(1)
                    .currency(Currency.TRY)
                    .conversationId("456d1297-908e-4bd6-a13b-4be31a6e47d5")
                    .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                    .paymentPhase(PaymentPhase.AUTH)
                    .card(Card.builder()
                            .cardHolderName("Haluk Demir")
                            .cardNumber("5258640000000001")
                            .expireYear("2044")
                            .expireMonth("07")
                            .cvc("000")
                            .build())
                    .items(items)
                    .build();

            PaymentResponse response = craftgate.payment().createPayment(request);
            System.out.println(String.format("Create Payment Result: %s", response));
        };
    }

}

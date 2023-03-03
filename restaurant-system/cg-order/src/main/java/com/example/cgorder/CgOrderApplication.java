package com.example.cgorder;

import com.example.cgorder.service.OrderServiceDummy;
import io.craftgate.Craftgate;
import io.craftgate.model.Currency;
import io.craftgate.model.PaymentGroup;
import io.craftgate.model.PaymentPhase;
import io.craftgate.request.CreatePaymentRequest;
import io.craftgate.request.dto.Card;
import io.craftgate.request.dto.PaymentItem;
import io.craftgate.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class CgOrderApplication {


    public static void main(String[] args) {
        SpringApplication.run(CgOrderApplication.class, args);
    }
}

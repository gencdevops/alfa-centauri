package com.example.kafkalogbackxmlv2;

import com.example.kafkalogbackxmlv2.model.entity.Account;
import com.example.kafkalogbackxmlv2.model.entity.Customer;
import com.example.kafkalogbackxmlv2.model.enums.Currency;
import com.example.kafkalogbackxmlv2.repository.AccountRepository;
import com.example.kafkalogbackxmlv2.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class KafkaLogbackXmlV2Application {

	public static void main(String[] args) {
		SpringApplication.run(KafkaLogbackXmlV2Application.class, args);
	}

	@Bean
    @Profile("!test")
	CommandLineRunner run(AccountRepository accountRepository,
						  CustomerRepository customerRepository) {
		return args -> {
			Customer customer1 = Customer.builder()
					.firstname("oguzcan")
					.lastname("bicer")
					.birthDate(LocalDate.of(1996, 1, 9))
					.email("oguzcanbicer96@gmail.com").build();
			customerRepository.save(customer1);

			Customer customer2 = Customer.builder()
					.firstname("abc")
					.lastname("xyz")
					.birthDate(LocalDate.of(2000, 1, 1))
					.email("abcxyz@gmail.com").build();
			customerRepository.save(customer2);

			Account account1 = Account.builder()
					.balance(BigDecimal.valueOf(10000L))
					.customer(customer1)
					.currency(Currency.TRY)
					.build();
			accountRepository.save(account1);

			Account account2 = Account.builder()
					.balance(BigDecimal.valueOf(300L))
					.customer(customer2)
					.currency(Currency.USD)
					.build();
			accountRepository.save(account2);

			Account account3 = Account.builder()
					.balance(BigDecimal.valueOf(1000000L))
					.customer(customer2)
					.currency(Currency.TRY)
					.build();
			accountRepository.save(account3);
		};
	}

}

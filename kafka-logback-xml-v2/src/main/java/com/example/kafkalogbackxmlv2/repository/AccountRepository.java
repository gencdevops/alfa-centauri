package com.example.kafkalogbackxmlv2.repository;

import com.example.kafkalogbackxmlv2.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

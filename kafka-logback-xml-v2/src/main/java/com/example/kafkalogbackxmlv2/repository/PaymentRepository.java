package com.example.kafkalogbackxmlv2.repository;

import com.example.kafkalogbackxmlv2.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

package com.example.kafkalogbackxmlv2.repository;

import com.example.kafkalogbackxmlv2.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

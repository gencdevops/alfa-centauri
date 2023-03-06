package com.example.cgorder.repository;

import com.example.cgorder.model.OrderIdempotent;
import com.example.cgorder.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderIdempotentRepository extends JpaRepository<OrderIdempotent, UUID> {
    Optional<OrderIdempotent> findByKey(String key);
}

package com.example.cgorder.repository;


import com.example.cgorder.model.OrderOutbox;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public interface OrderOutboxRepository extends JpaRepository<OrderOutbox, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Page<OrderOutbox> findAll(Pageable pageable);



}

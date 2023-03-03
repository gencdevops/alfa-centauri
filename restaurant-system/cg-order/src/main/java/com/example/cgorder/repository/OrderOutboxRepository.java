package com.example.cgorder.repository;


import com.example.cgorder.model.OrderOutbox;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface OrderOutboxRepository extends JpaRepository<OrderOutbox, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Page<OrderOutbox> findAllByOrderByIdAsc(Pageable pageable);
}

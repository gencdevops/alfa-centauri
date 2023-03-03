package com.example.cgorder.service;

import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.repository.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderOutBoxService {
    private final OrderOutboxRepository orderOutboxRepository;

    public void saveOrderOutbox(OrderOutbox orderOutbox) {
        orderOutboxRepository.save(orderOutbox);

    }

    public void deleteOrderOutbox(UUID orderOutboxId) {
        orderOutboxRepository.deleteById(orderOutboxId);
    }
}

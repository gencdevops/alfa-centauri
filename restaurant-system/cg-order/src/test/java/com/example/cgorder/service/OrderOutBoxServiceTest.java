package com.example.cgorder.service;

import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.repository.OrderOutboxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OrderOutBoxServiceTest {
    @InjectMocks
    private OrderOutBoxService orderOutboxService;
    @Mock
    private OrderOutboxRepository orderOutboxRepository;

    @Test
    void shouldSaveOutboxRecord() {
        OrderOutbox orderOutbox = new OrderOutbox();
        orderOutboxService.saveOrderOutbox(orderOutbox);
        Mockito.verify(orderOutboxRepository, times(1)).save(orderOutbox);
    }

    @Test
    void shouldDeleteOutboxRecord() {
        UUID orderOutboxId = UUID.randomUUID();
        orderOutboxService.deleteOrderOutbox(orderOutboxId);
        Mockito.verify(orderOutboxRepository, times(1)).deleteById(orderOutboxId);
    }
}
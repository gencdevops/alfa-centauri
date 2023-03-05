package com.example.cgorder.scheduler;

import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.repository.OrderOutboxRepository;
import com.example.cgorder.service.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutBoxRetryServiceTest {

    @Mock
    OrderOutboxRepository orderOutboxRepository;

    @Mock
    ProducerService producerService;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    OutBoxRetryService outBoxRetryService;

    @Test
    void retry() {
        Page<OrderOutbox> page = Page.empty();
        when(orderOutboxRepository.findAll(Pageable.ofSize(10))).thenReturn(page);

        outBoxRetryService.retry();

        verify(producerService, times(0)).sendMessage(any());
        verify(orderOutboxRepository, times(0)).deleteById(any());
    }
}
package com.example.cgorder.service;

import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaOrderEventOperationsListenerServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    KafkaOrderEventOperationsListenerService kafkaOrderEventOperationsListenerService;

    @Test
    void shouldSetStatusAndSaveOrder() throws JsonProcessingException {
        UUID uuid = UUID.randomUUID();

        Order mockOrder = Mockito.spy(Order.class);
        mockOrder.setOrderId(uuid);

        when(objectMapper.readValue(any(String.class), any(Class.class))).thenReturn(mockOrder);
        when(orderRepository.findById(uuid)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        kafkaOrderEventOperationsListenerService.handleMessage("test", "test");

        verify(mockOrder).setOrderStatus(OrderStatus.PREPARING);
        verify(orderRepository).save(mockOrder);

    }
}
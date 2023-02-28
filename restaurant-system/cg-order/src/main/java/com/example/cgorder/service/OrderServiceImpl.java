package com.example.cgorder.service;

import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDto save(OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public void delete(UUID id) {
        orderRepository.delete(orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found")
        ));
    }

    @Override
    public OrderResponseDto update(OrderRequestDto orderRequestDto, UUID id) {
        return null;
    }
}

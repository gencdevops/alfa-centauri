package com.example.cgorder.service;

import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.exception.OrderNotFoundException;
import com.example.cgorder.mapper.OrderItemMapper;
import com.example.cgorder.mapper.OrderMapper;
import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderItem;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).toList();
    }

    @Override
    public OrderResponseDto getOrderById(UUID id) {
        Order orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return orderMapper.toDto(orderEntity);
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        var order = orderMapper.toEntity(orderRequestDto);
        var orderItemList = orderRequestDto.getOrderItems().stream().map(orderItemMapper::toEntity).toList();
        order.setOrderItems(orderItemList);
        order.setStatus(OrderStatus.RECEIVED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(UUID id) {
        Order orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        orderRepository.delete(orderEntity);
    }

}


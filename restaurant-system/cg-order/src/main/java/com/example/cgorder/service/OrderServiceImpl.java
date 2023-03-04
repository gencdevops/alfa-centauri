package com.example.cgorder.service;

import com.example.cgorder.client.CardDto;
import com.example.cgorder.client.OrderRequestDto;
import com.example.cgorder.client.OrderResponseDto;
import com.example.cgorder.exception.OrderNotFoundException;
import com.example.cgorder.mapper.OrderItemMapper;
import com.example.cgorder.mapper.OrderMapper;
import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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

    private final ObjectMapper objectMapper;

    private final ProducerService producerService;

    private final OrderOutBoxService orderOutBoxService;

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).toList();
    }

    @Override
    public OrderResponseDto getOrderById(UUID id) {
        Order orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        var orderResponseDtos =
                orderEntity.getOrderItems().stream().map(orderItemMapper::toDto).toList();

        var responseDto = orderMapper.toDto(orderEntity);
        responseDto.setOrderItems(orderResponseDtos);
        return responseDto;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, CardDto cardDto) {
        var order = orderMapper.toEntity(orderRequestDto);

        // TODO : order'dan orderOutbox Mapper yazilacak

        var orderItemList = orderRequestDto.getOrderItems().stream().map(orderItemMapper::toEntity).toList();
        order.setOrderItems(orderItemList);
        order.setOrderStatus(OrderStatus.RECEIVED);


        OrderOutbox orderOutbox;
        try {
            orderOutbox = OrderOutbox.builder()
                    .orderPayload(objectMapper.writeValueAsString(order))
                    .orderOutboxId(order.getOrderId())
                    .transactionId("12345667899032")
                    .build();
            orderOutBoxService.saveOrderOutbox(orderOutbox);
        } catch (JsonProcessingException e) {
            // TODO : burada exception handlerda yakalayalim mi? mappera alinca MapStruct icerisinde kontrol et
            throw new RuntimeException(e);
        }

        producerService.sendMessage(order);


        orderOutBoxService.deleteOrderOutbox(orderOutbox.getOrderOutboxId());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(UUID id) {
        Order orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        orderRepository.delete(orderEntity);
    }

}

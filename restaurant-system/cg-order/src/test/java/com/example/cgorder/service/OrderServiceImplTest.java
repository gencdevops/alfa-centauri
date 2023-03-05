package com.example.cgorder.service;

import com.example.cgcommon.model.CardInfoDto;
import com.example.cgorder.dto.OrderItemRequestDTO;
import com.example.cgorder.dto.OrderResponseDto;
import com.example.cgorder.dto.PlaceOrderRequestDTO;
import com.example.cgorder.mapper.OrderItemMapper;
import com.example.cgorder.mapper.OrderMapper;
import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderItem;
import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {


    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock private OrderRepository orderRepository;

    @Mock private OrderMapper orderMapper;

    @Mock private OrderItemMapper orderItemMapper;

    @Mock private ObjectMapper objectMapper;
    @Mock private ProducerService producerService;

    @Mock private OrderOutBoxService orderOutBoxService;
    @Captor
    private ArgumentCaptor<OrderOutbox> orderOutboxCaptor;

    @Test
    void placeOrder() throws JsonProcessingException {
        OrderItemRequestDTO orderItemRequestDTO = OrderItemRequestDTO.builder()
                .productId(1L)
                .quantity(1)
                .build();

        CardInfoDto cardInfoDto = CardInfoDto.builder()
                .cardNumber("123456789")
                .cvc("233")
                .build();
        PlaceOrderRequestDTO placeOrderRequestDTO = PlaceOrderRequestDTO.builder()
                .totalPrice(BigDecimal.ONE)
                .orderItems(List.of(orderItemRequestDTO))
                .cardInfo(cardInfoDto)
                .build();

        Order order = new Order();
        OrderResponseDto expectedOrderResponseDto = new OrderResponseDto();

        when(orderMapper.convertOrderFromPlaceOrderRequestDTO(placeOrderRequestDTO)).thenReturn(order);
        when(orderItemMapper.convertOrderItemFromOrderItemRequestDTO(any())).thenReturn(new OrderItem());
        when(objectMapper.writeValueAsString(any(Order.class))).thenReturn("");
        doNothing().when(orderOutBoxService).saveOrderOutbox(any(OrderOutbox.class));
        doNothing().when(producerService).sendMessage(any(Order.class));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.convertPlaceOrderRequestDTOFromOrder(order)).thenReturn(expectedOrderResponseDto);

        OrderResponseDto actualOrderResponseDto = orderService.placeOrder(placeOrderRequestDTO);

        verify(orderRepository, times(1)).save(order);
        verify(producerService, times(1)).sendMessage(any(Order.class));
        verify(orderOutBoxService, times(1)).deleteOrderOutbox(any());
        verify(orderOutBoxService, times(1)).saveOrderOutbox(orderOutboxCaptor.capture());

        assertEquals(expectedOrderResponseDto, actualOrderResponseDto);
        assertEquals(order.getOrderId(), orderOutboxCaptor.getValue().getOrderOutboxId());
    }
}
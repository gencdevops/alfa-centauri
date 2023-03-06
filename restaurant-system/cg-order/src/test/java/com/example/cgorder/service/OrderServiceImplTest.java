package com.example.cgorder.service;

import com.example.cgcommon.configuration.CacheClient;
import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.dto.response.ProductStatusCacheDto;
import com.example.cgcommon.model.CardInfoDto;
import com.example.cgcommon.model.ProductStatus;
import com.example.cgcommon.request.OrderItemRequestDTO;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgorder.exception.InConsistentProductPriceException;
import com.example.cgorder.exception.ProductPriceNotFoundException;
import com.example.cgorder.feign.ProductFeignClient;
import com.example.cgorder.mapper.OrderItemMapper;
import com.example.cgorder.mapper.OrderMapper;
import com.example.cgorder.model.*;
import com.example.cgorder.repository.OrderIdempotentRepository;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    @Spy
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProducerService producerService;

    @Mock
    private OrderOutBoxService orderOutBoxService;

    @Mock
    private ProductFeignClient productFeignClient;

    @Mock
    OrderIdempotentRepository idempotentRepository;

    @Mock
    CacheClient cacheClient;

    @Captor
    private ArgumentCaptor<OrderOutbox> orderOutboxCaptor;

//    @Test
//    void placeOrder() throws JsonProcessingException {
//        UUID branchId = UUID.randomUUID();
//        UUID productId = UUID.randomUUID();
//
//        OrderItemRequestDTO orderItemRequestDTO = OrderItemRequestDTO.builder()
//                .productId(UUID.randomUUID())
//                .quantity(1)
//                .build();
//
//        CardInfoDto cardInfoDto = CardInfoDto.builder()
//                .cardNumber("123456789")
//                .cvc("233")
//                .build();
//
//        PlaceOrderRequestDTO placeOrderRequestDTO = PlaceOrderRequestDTO.builder()
//                .totalPrice(BigDecimal.ONE)
//                .orderItems(List.of(orderItemRequestDTO))
//                .cardInfo(cardInfoDto)
//                .build();
//
//        Order order = new Order();
//        OrderResponseDTO expectedOrderResponseDto = new OrderResponseDTO();
//
//        List<ProductPriceResponseDto> productPriceResponseDtoList = List.of(new ProductPriceResponseDto(branchId, productId, BigDecimal.ONE));
//
//        when(orderMapper.convertOrderFromPlaceOrderRequestDTO(placeOrderRequestDTO)).thenReturn(order);
//        when(orderItemMapper.convertOrderItemFromOrderItemRequestDTO(any())).thenReturn(new OrderItem());
//        when(objectMapper.writeValueAsString(any(Order.class))).thenReturn("");
//        doNothing().when(orderOutBoxService).saveOrderOutbox(any(OrderOutbox.class));
//        doNothing().when(producerService).sendMessage(any(Order.class));
//        when(orderRepository.save(order)).thenReturn(order);
//        when(productFeignClient.getProductPrices(any(), any())).thenReturn(productPriceResponseDtoList);
//        when(orderMapper.convertPlaceOrderRequestDTOFromOrder(order)).thenReturn(expectedOrderResponseDto);
//        when(idempotentRepository.findByKey(any())).thenReturn(Optional.empty());
//
//        OrderResponseDTO actualOrderResponseDto = orderService.placeOrder(placeOrderRequestDTO, "test");
//
//        verify(orderRepository, times(1)).save(order);
//        verify(producerService, times(1)).sendMessage(any(Order.class));
//        verify(orderOutBoxService, times(1)).deleteOrderOutbox(any());
//        verify(orderOutBoxService, times(1)).saveOrderOutbox(orderOutboxCaptor.capture());
//
//        assertEquals(expectedOrderResponseDto, actualOrderResponseDto);
//        assertEquals(order.getOrderId(), orderOutboxCaptor.getValue().getOrderOutboxId());
//    }

    @Test
    void placeOrder() throws JsonProcessingException {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        OrderItemRequestDTO orderItemRequestDTO = OrderItemRequestDTO.builder()
                .productId(productId)
                .quantity(1)
                .build();

        CardInfoDto cardInfoDto = CardInfoDto.builder()
                .cardNumber("123456789")
                .cvc("233")
                .build();

        when(productFeignClient.getProductPrices(any(), any())).thenReturn(List.of(new ProductPriceResponseDto(branchId, productId, BigDecimal.ONE)));
        doNothing().when(orderService).validateOrderStatus(any());
        doNothing().when(orderService).validateProductPrice(any(), any());
        when(orderItemMapper.convertOrderItemFromOrderItemRequestDTO(any())).thenReturn(OrderItem.builder().build());
        when(orderMapper.convertOrderFromPlaceOrderRequestDTO(any())).thenReturn(
                Order.builder().orderItems(List.of(OrderItem.builder()
                        .totalPrice(BigDecimal.ONE)
                        .quantity(1)
                        .unitPrice(BigDecimal.ONE)
                        .productId(productId)
                        .build())).orderStatus(OrderStatus.RECEIVED).build());


        new PlaceOrderRequestDTO(List.of(orderItemRequestDTO), BigDecimal.ONE, cardInfoDto, branchId);


    }


    @Test
    void shouldCreateIdempotentKeyWhenIdempotentNotFound() {
        when(idempotentRepository.findByKey(any())).thenReturn(Optional.empty());

        String idempotentKey = orderService.createIdempotentKey();

        verify(idempotentRepository).save(any(OrderIdempotent.class));
        assertNotNull(idempotentKey);
    }

    @Test
    void shouldReturnIdempotentKeyWhenIdempotentFound() {
        OrderIdempotent orderIdempotent = OrderIdempotent.builder()
                .key(UUID.randomUUID().toString())
                .build();

        when(idempotentRepository.findByKey(any())).thenReturn(Optional.of(orderIdempotent));

        String idempotentKey = orderService.createIdempotentKey();

        verify(idempotentRepository,times(0)).save(any(OrderIdempotent.class));
        assertEquals(orderIdempotent.getKey(), idempotentKey);
    }

    @Test
    void shouldValidateProductPriceWithoutAnyException() {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        List<ProductPriceResponseDto>  productPrices = List.of(new ProductPriceResponseDto(branchId, productId, BigDecimal.ONE));
        List<OrderItemRequestDTO> orderItems = List.of(OrderItemRequestDTO.builder().productId(productId).unitPrice(BigDecimal.ONE).quantity(1).totalPrice(BigDecimal.ONE).build());

        when(productFeignClient.getProductPrices(any(), any())).thenReturn(productPrices);

        assertDoesNotThrow(() -> orderService.validateProductPrice(branchId, orderItems));
    }

    @Test
    void shouldThrowProductPriceNotFoundExceptionWhenProductPriceNotFound() {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        List<OrderItemRequestDTO> orderItems = List.of(OrderItemRequestDTO.builder().productId(productId).unitPrice(BigDecimal.ONE).quantity(1).totalPrice(BigDecimal.ONE).build());

        when(productFeignClient.getProductPrices(any(), any())).thenReturn(new ArrayList<>());

        assertThrows(ProductPriceNotFoundException.class, () -> orderService.validateProductPrice(branchId, orderItems));
    }

    @Test
    void shouldThrowInConsistentProductPriceExceptionWhenProductPriceDoesntMatch() {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        List<ProductPriceResponseDto>  productPrices = List.of(new ProductPriceResponseDto(branchId, productId, BigDecimal.ONE));
        List<OrderItemRequestDTO> orderItems = List.of(OrderItemRequestDTO.builder().productId(productId).unitPrice(BigDecimal.TEN).quantity(1).totalPrice(BigDecimal.TEN).build());

        when(productFeignClient.getProductPrices(any(), any())).thenReturn(productPrices);

        assertThrows(InConsistentProductPriceException.class, () -> orderService.validateProductPrice(branchId, orderItems));
    }

    @Test
    void shouldThrowInConsistentProductPriceExceptionWhenTotalPriceDoesntMatch() {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        List<ProductPriceResponseDto>  productPrices = List.of(new ProductPriceResponseDto(branchId, productId, BigDecimal.ONE));
        List<OrderItemRequestDTO> orderItems = List.of(OrderItemRequestDTO.builder().productId(productId).unitPrice(BigDecimal.TEN).quantity(1).totalPrice(BigDecimal.TEN).build());

        when(productFeignClient.getProductPrices(any(), any())).thenReturn(productPrices);

        assertThrows(InConsistentProductPriceException.class, () -> orderService.validateProductPrice(branchId, orderItems));
    }

    @Test
    void shouldValidateOrderStatus() {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        List<OrderItemRequestDTO> orderItems = List.of(OrderItemRequestDTO.builder().productId(productId).unitPrice(BigDecimal.TEN).quantity(1).totalPrice(BigDecimal.TEN).build());

        when(cacheClient.get(any())).thenReturn(ProductStatusCacheDto.builder().productStatus(ProductStatus.ACTIVE).build());

        orderService.validateOrderStatus(orderItems);

        verify(cacheClient, times(orderItems.size())).get(any());
    }

    @Test
    void shouldThrowProductPriceNotFoundExceptionStatusIsPassive() {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        List<OrderItemRequestDTO> orderItems = List.of(OrderItemRequestDTO.builder().productId(productId).unitPrice(BigDecimal.TEN).quantity(1).totalPrice(BigDecimal.TEN).build());

        when(cacheClient.get(any())).thenReturn(ProductStatusCacheDto.builder().productStatus(ProductStatus.PASSIVE).build());

        assertThrows(ProductPriceNotFoundException.class, () -> orderService.validateOrderStatus(orderItems));
    }

    @Test
    void updateIdempotentStatus() {
        OrderIdempotent orderIdempotent = mock(OrderIdempotent.class);
        OrderIdemPotentStatus orderIdemPotentStatus = OrderIdemPotentStatus.AVAILABLE;

        orderService.updateIdempotentStatus(orderIdempotent, orderIdemPotentStatus);

        verify(orderIdempotent).setOrderIdemPotentStatus(orderIdemPotentStatus);
        verify(idempotentRepository).save(orderIdempotent);

    }
}
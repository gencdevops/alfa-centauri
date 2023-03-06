package com.example.cgorder.service;

import com.example.cgcommon.configuration.CacheClient;
import com.example.cgcommon.dto.response.OrderItemResponseDTO;
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

    @Test
    void placeOrder() throws JsonProcessingException {
        UUID branchId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        String key = UUID.randomUUID().toString();

        OrderItemRequestDTO orderItemRequestDTO = OrderItemRequestDTO.builder()
                .productId(productId)
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(1001))
                .totalPrice(BigDecimal.valueOf(1001))
                .build();
        ArrayList<OrderItemRequestDTO> orderItemRequestDTOS = new ArrayList();
        orderItemRequestDTOS.add(orderItemRequestDTO);

        CardInfoDto cardInfoDto = CardInfoDto.builder()
                .cardNumber("123456789")
                .cvc("233")
                .build();

        PlaceOrderRequestDTO placeOrderRequestDTO = PlaceOrderRequestDTO.builder()
                .branchId(branchId)
                .orderItems(orderItemRequestDTOS)
                .cardInfo(cardInfoDto)
                .totalPrice(BigDecimal.valueOf(1001))
                .build();

        OrderItem orderItem = OrderItem.builder()
                .orderItemId(productId)
                .totalPrice(BigDecimal.valueOf(1001))
                .unitPrice(BigDecimal.valueOf(1001))
                .quantity(1)
                .productId(productId)
                .build();

        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .totalPrice(BigDecimal.valueOf(1001))
                .build();

        ProductPriceResponseDto productPriceResponseDto = new ProductPriceResponseDto(branchId, productId, BigDecimal.valueOf(1001));
        ProductStatusCacheDto productStatusCacheDto = ProductStatusCacheDto.builder()
                .productId(productId)
                .productStatus(ProductStatus.ACTIVE)
                .build();

        OrderItemResponseDTO orderItemResponseDTO = OrderItemResponseDTO.builder()
                .orderItemId(productId)
                .unitPrice(BigDecimal.valueOf(1001))
                .productId(productId)
                .quantity(1)
                .build();
        List<OrderItemResponseDTO> orderItemResponseDTOS = new ArrayList<>();
        orderItemResponseDTOS.add(orderItemResponseDTO);

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .totalPrice(BigDecimal.valueOf(1001))
                .orderItems(orderItemResponseDTOS)
                .build();

        when(idempotentRepository.findByKey(any())).thenReturn(Optional.of(new OrderIdempotent(productId, key, OrderIdemPotentStatus.AVAILABLE)));
        when(productFeignClient.getProductPrices(any(), any())).thenReturn(List.of(productPriceResponseDto));
        when(orderMapper.convertOrderFromPlaceOrderRequestDTO(placeOrderRequestDTO)).thenReturn(order);
        when(cacheClient.get(any())).thenReturn(productStatusCacheDto);
        when(orderItemMapper.convertOrderItemFromOrderItemRequestDTO(any())).thenReturn(orderItem);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderMapper.convertPlaceOrderRequestDTOFromOrder(any())).thenReturn(orderResponseDTO);
        OrderResponseDTO result = orderService.placeOrder(placeOrderRequestDTO, key);

        assertEquals(BigDecimal.valueOf(1001), result.getTotalPrice());
        assertEquals(orderItemResponseDTOS, result.getOrderItems());
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
package com.example.cgrestaurant.service;

import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.model.CardInfoDto;
import com.example.cgrestaurant.dto.request.RestaurantOrderItemRequestDto;
import com.example.cgrestaurant.dto.request.RestaurantOrderRequestDto;
import com.example.cgrestaurant.dto.request.order.OrderItemRequestDTO;
import com.example.cgrestaurant.dto.request.order.PlaceOrderRequestDTO;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.feign.OrderFeignClient;
import com.example.cgrestaurant.model.Product;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderFeignClient orderFeignClient;
    @Mock
    private ProductService productService;
    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        List<RestaurantOrderItemRequestDto> dtoList = new ArrayList<>();
        dtoList.add(RestaurantOrderItemRequestDto.builder().productName(RandomStringUtils.random(10)).quantity(1).build());
        RestaurantOrderRequestDto restaurantOrderRequestDto = new RestaurantOrderRequestDto(dtoList, null, null);

        when(productService.getProductByName(anyString())).thenThrow(new ProductNotFoundException("Product not found"));

        assertThrows(ProductNotFoundException.class, () -> orderService.placeOrder(restaurantOrderRequestDto));
    }

    @Test
    void shouldPlaceOrder() {
        // given
        String randomProduct = RandomStringUtils.random(10);
        String randomProduct2 = RandomStringUtils.random(10);
        BigDecimal defaultPrice1 = BigDecimal.valueOf(99);
        BigDecimal defaultPrice2 = BigDecimal.valueOf(109);
        int quantity1 = 5;
        int quantity2 = 6;
        BigDecimal expectedTotalPrice = defaultPrice1.multiply(BigDecimal.valueOf(quantity1)).add(defaultPrice2.multiply(BigDecimal.valueOf(quantity2)));
        CardInfoDto cardInfoDTO = CardInfoDto.builder().build();

        Product product1 = Product.builder()
                .productName(randomProduct)
                .build();
        Product product2 = Product.builder()
                .productName(randomProduct2)
                .build();

        when(productService.getProductByName(randomProduct)).thenReturn(product1);
        when(productService.getProductByName(randomProduct2)).thenReturn(product2);
        when(orderFeignClient.placeOrder(any())).thenReturn(OrderResponseDTO.builder().totalPrice(expectedTotalPrice).build());

        RestaurantOrderItemRequestDto itemRequestDTO1 = RestaurantOrderItemRequestDto.builder()
                .productName(randomProduct)
                .quantity(quantity1)
                .build();
        RestaurantOrderItemRequestDto itemRequestDTO2 = RestaurantOrderItemRequestDto.builder()
                .productName(randomProduct2)
                .quantity(quantity2)
                .build();

        ArgumentCaptor<PlaceOrderRequestDTO> placeOrderRequestDTOCaptor = ArgumentCaptor.forClass(PlaceOrderRequestDTO.class);
        List<RestaurantOrderItemRequestDto> itemRequestDTOs = Arrays.asList(itemRequestDTO1, itemRequestDTO2);

        RestaurantOrderRequestDto requestDto = new RestaurantOrderRequestDto(itemRequestDTOs, UUID.randomUUID(), cardInfoDTO);

        // when
        OrderResponseDTO responseDTO = orderService.placeOrder(requestDto);

        // then
        verify(productService).getProductByName(randomProduct);
        verify(productService).getProductByName(randomProduct2);
        verify(orderFeignClient).placeOrder(placeOrderRequestDTOCaptor.capture());

        PlaceOrderRequestDTO capturedRequestDTO = placeOrderRequestDTOCaptor.getValue();
        List<OrderItemRequestDTO> expectedOrderItemRequestDTOs = Arrays.asList(
                OrderItemRequestDTO.builder()
                        .productName(randomProduct)
                        .quantity(quantity1)
                        .unitPrice(defaultPrice1)
                        .totalPrice(defaultPrice1.multiply(BigDecimal.valueOf(quantity1)))
                        .build(),
                OrderItemRequestDTO.builder()
                        .productName(randomProduct2)
                        .quantity(quantity2)
                        .unitPrice(defaultPrice2)
                        .totalPrice(defaultPrice2.multiply(BigDecimal.valueOf(quantity2)))
                        .build()
        );
        assertEquals(expectedOrderItemRequestDTOs, capturedRequestDTO.getOrderItems());
        assertEquals(expectedTotalPrice, capturedRequestDTO.getTotalPrice());
        assertEquals(expectedTotalPrice, responseDTO.getTotalPrice());


    }

}


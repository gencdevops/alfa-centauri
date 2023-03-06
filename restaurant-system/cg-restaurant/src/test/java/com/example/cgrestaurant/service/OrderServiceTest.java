package com.example.cgrestaurant.service;

import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.model.CardInfoDto;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgrestaurant.dto.request.RestaurantOrderItemRequestDto;
import com.example.cgrestaurant.dto.request.RestaurantOrderRequestDto;
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

        dtoList.add(RestaurantOrderItemRequestDto.builder().quantity(1).build());
        RestaurantOrderRequestDto restaurantOrderRequestDto = new RestaurantOrderRequestDto(dtoList, UUID.randomUUID().toString(), UUID.randomUUID(), null);

        when(productService.findByProductId(any())).thenThrow(new ProductNotFoundException("Product not found"));

        assertThrows(ProductNotFoundException.class, () -> orderService.placeOrder(restaurantOrderRequestDto));
    }

    @Test
    void shouldPlaceOrder() {
        // given
        UUID randomProductUUID = UUID.randomUUID();
        UUID randomProduct2UUID = UUID.randomUUID();
        BigDecimal defaultPrice1 = BigDecimal.valueOf(100);
        BigDecimal defaultPrice2 = BigDecimal.valueOf(100);
        int quantity1 = 5;
        int quantity2 = 6;
        BigDecimal expectedTotalPrice = defaultPrice1.multiply(BigDecimal.valueOf(quantity1)).add(defaultPrice2.multiply(BigDecimal.valueOf(quantity2)));
        CardInfoDto cardInfoDTO = CardInfoDto.builder().build();

        Product product1 = Product.builder()
                .productName(RandomStringUtils.random(10))
                .build();
        Product product2 = Product.builder()
                .productName(RandomStringUtils.random(10))
                .build();

        ProductPriceResponseDto productPricesRequestDto = new ProductPriceResponseDto(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.valueOf(100));

        when(productService.findByProductId(randomProductUUID)).thenReturn(product1);
        when(productService.findByProductId(randomProduct2UUID)).thenReturn(product2);
        when(productService.getProductPrice(any(), any())).thenReturn(productPricesRequestDto);
        when(orderFeignClient.placeOrder(any(), any())).thenReturn(OrderResponseDTO.builder().totalPrice(expectedTotalPrice).build());

        RestaurantOrderItemRequestDto itemRequestDTO1 = RestaurantOrderItemRequestDto.builder()
                .productId(randomProductUUID)
                .quantity(quantity1)
                .build();
        RestaurantOrderItemRequestDto itemRequestDTO2 = RestaurantOrderItemRequestDto.builder()
                .productId(randomProduct2UUID)
                .quantity(quantity2)
                .build();

        ArgumentCaptor<PlaceOrderRequestDTO> placeOrderRequestDTOCaptor = ArgumentCaptor.forClass(PlaceOrderRequestDTO.class);
        List<RestaurantOrderItemRequestDto> itemRequestDTOs = Arrays.asList(itemRequestDTO1, itemRequestDTO2);

        RestaurantOrderRequestDto requestDto = new RestaurantOrderRequestDto(itemRequestDTOs, UUID.randomUUID().toString(), UUID.randomUUID(), cardInfoDTO);

        // when
        OrderResponseDTO responseDTO = orderService.placeOrder(requestDto);

        // then
        verify(productService).findByProductId(randomProductUUID);
        verify(productService).findByProductId(randomProduct2UUID);
        verify(orderFeignClient).placeOrder(any(), placeOrderRequestDTOCaptor.capture());

        PlaceOrderRequestDTO capturedRequestDTO = placeOrderRequestDTOCaptor.getValue();

        assertEquals(expectedTotalPrice, capturedRequestDTO.getTotalPrice());
        assertEquals(expectedTotalPrice, responseDTO.getTotalPrice());
    }

}


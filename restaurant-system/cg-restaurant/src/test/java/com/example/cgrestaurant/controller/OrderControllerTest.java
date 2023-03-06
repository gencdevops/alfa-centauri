package com.example.cgrestaurant.controller;

import com.example.cgcommon.dto.response.OrderItemResponseDTO;
import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.model.CardInfoDto;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgrestaurant.BaseIntegrationTest;
import com.example.cgrestaurant.dto.request.RestaurantOrderItemRequestDto;
import com.example.cgrestaurant.dto.request.RestaurantOrderRequestDto;
import com.example.cgrestaurant.feign.OrderFeignClient;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.model.enums.ProductStatus;
import com.example.cgrestaurant.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    private Product alreadyExistProduct;

    @MockBean
    private OrderFeignClient orderFeignClient;

    @BeforeEach
    void setUp() {
        alreadyExistProduct = productRepository.save(
                Product.builder()
                        .id(UUID.randomUUID())
                        .productName(RandomStringUtils.random(10))
                        .productStatus(ProductStatus.ACTIVE)
                        .build());
    }

    @Test
    void placeOrder() throws Exception {
        RestaurantOrderItemRequestDto restaurantOrderItemRequestDto = new RestaurantOrderItemRequestDto(
                alreadyExistProduct.getId(), 5
        );

        CardInfoDto cardInfoDto = CardInfoDto.builder().build();
        RestaurantOrderRequestDto restaurantOrderRequestDto = new RestaurantOrderRequestDto(
                List.of(restaurantOrderItemRequestDto), UUID.randomUUID(), cardInfoDto
        );

        OrderItemResponseDTO orderItemResponseDTO = OrderItemResponseDTO.builder()
                .orderItemId(restaurantOrderItemRequestDto.getProductId())
                .quantity(restaurantOrderItemRequestDto.getQuantity())
                .build();

        Mockito.when(orderFeignClient.placeOrder(Mockito.any(PlaceOrderRequestDTO.class)))
                .thenReturn(OrderResponseDTO.builder()
                        .orderItems(List.of(orderItemResponseDTO))
                        .build());

        this.mockMvc.perform(post(BASE_ORDER_ENDPOINT + "/place-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(restaurantOrderRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItems[0].productId").value(restaurantOrderRequestDto.restaurantOrderItemRequestDtos().get(0).getProductId()));

    }

}
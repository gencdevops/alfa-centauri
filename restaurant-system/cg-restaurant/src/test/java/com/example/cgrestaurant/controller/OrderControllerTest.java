package com.example.cgrestaurant.controller;

import com.example.cgcommon.dto.response.OrderItemResponseDTO;
import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.model.CardInfoDto;
import com.example.cgcommon.model.ProductStatus;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgrestaurant.BaseIntegrationTest;
import com.example.cgrestaurant.dto.request.RestaurantOrderItemRequestDto;
import com.example.cgrestaurant.dto.request.RestaurantOrderRequestDto;
import com.example.cgrestaurant.feign.OrderFeignClient;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.model.ProductPrice;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.BranchRepository;
import com.example.cgrestaurant.repository.ProductPriceRepository;
import com.example.cgrestaurant.repository.ProductRepository;
import com.example.cgrestaurant.repository.SupplierRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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
    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private BranchRepository branchRepository;
    private Product alreadyExistProduct;
    private ProductPrice alreadyExistProductPrice;

    @MockBean
    private OrderFeignClient orderFeignClient;

    @BeforeEach
    void setUp() {
        UUID productId = UUID.randomUUID();
        UUID branchId = UUID.randomUUID();
        UUID supplierId = UUID.randomUUID();

        supplierRepository.save(
                Supplier.builder()
                        .id(supplierId)
                        .supplierName("test supplier")
                        .build()
        );

        branchRepository.save(
                Branch.builder()
                        .supplierId(supplierId)
                        .branchName("test branch")
                        .id(branchId)
                        .build()
        );

        alreadyExistProduct = productRepository.save(
                Product.builder()
                        .id(productId)
                        .productName(RandomStringUtils.random(10))
                        .productStatus(ProductStatus.ACTIVE)
                        .supplierId(supplierId)
                        .build());

        alreadyExistProductPrice = productPriceRepository.save(
                ProductPrice.builder()
                        .price(BigDecimal.valueOf(100))
                        .productId(productId)
                        .priceId(UUID.randomUUID())
                        .branchId(branchId)
                        .build()
        );
    }

    @Test
    @Disabled
    void placeOrder() throws Exception {
        RestaurantOrderItemRequestDto restaurantOrderItemRequestDto = new RestaurantOrderItemRequestDto(
                alreadyExistProduct.getId(), 1
        );

        CardInfoDto cardInfoDto = CardInfoDto.builder().build();
        RestaurantOrderRequestDto restaurantOrderRequestDto = new RestaurantOrderRequestDto(
                List.of(restaurantOrderItemRequestDto), UUID.randomUUID().toString(), alreadyExistProductPrice.getBranchId() , cardInfoDto
        );

        OrderItemResponseDTO orderItemResponseDTO = OrderItemResponseDTO.builder()
                .unitPrice(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .orderItemId(restaurantOrderItemRequestDto.getProductId())
                .quantity(restaurantOrderItemRequestDto.getQuantity())
                .build();

        Mockito.when(orderFeignClient.placeOrder(Mockito.any(String.class), Mockito.any(PlaceOrderRequestDTO.class)))
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
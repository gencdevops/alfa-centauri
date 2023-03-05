package com.example.cgorder.service;


import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.request.ProductPricesRequestDto;
import com.example.cgorder.dto.OrderItemRequestDTO;
import com.example.cgorder.dto.OrderResponseDto;
import com.example.cgorder.dto.PlaceOrderRequestDTO;
import com.example.cgorder.exception.InConsistentProductPriceException;
import com.example.cgorder.exception.OrderPayloadDeserializeException;
import com.example.cgorder.exception.ProductPriceNotFoundException;
import com.example.cgorder.feign.ProductFeignClient;
import com.example.cgorder.mapper.OrderItemMapper;
import com.example.cgorder.mapper.OrderMapper;
import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final ObjectMapper objectMapper;
    private final ProducerService producerService;

    private final OrderOutBoxService orderOutBoxService;

    private final ProductFeignClient productFeignClient;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(@NotNull PlaceOrderRequestDTO placeOrderRequestDTO) {

        validateProductPrice(placeOrderRequestDTO.getBranchId(), placeOrderRequestDTO.getOrderItems());

        var order = orderMapper.convertOrderFromPlaceOrderRequestDTO(placeOrderRequestDTO);

        var orderItemList = placeOrderRequestDTO.getOrderItems().stream()
                .map(orderItemMapper::convertOrderItemFromOrderItemRequestDTO).toList();

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
            throw new OrderPayloadDeserializeException(e.getMessage());
        }
        producerService.sendMessage(order);

        orderOutBoxService.deleteOrderOutbox(orderOutbox.getOrderOutboxId());

        return orderMapper.convertPlaceOrderRequestDTOFromOrder(orderRepository.save(order));
    }

    public void validateProductPrice(UUID branchId, List<OrderItemRequestDTO> orderItems) {
        ProductPricesRequestDto productPricesRequestDto = ProductPricesRequestDto.builder()
                .productIds(orderItems.stream().map(OrderItemRequestDTO::getProductId).toList())
                .build();

        List<ProductPriceResponseDto>  productPrices = productFeignClient.getProductPrices(branchId,productPricesRequestDto);

        //Performans iyilestirmesi icin map'e attik
        Map<UUID, ProductPriceResponseDto> productIdPriceMap = productPrices.stream()
                .collect(Collectors.toMap(ProductPriceResponseDto::productId, Function.identity()));


        for (OrderItemRequestDTO orderItem : orderItems) {
            ProductPriceResponseDto productPrice = productIdPriceMap.get(orderItem.getProductId());

            if(Objects.isNull(productPrice)){
                throw new ProductPriceNotFoundException();
            }
            if(!orderItem.getUnitPrice().equals(productPrice.price())) {
                throw new InConsistentProductPriceException("Product price not match");
            }

            if(!orderItem.getTotalPrice().equals(productPrice.price().multiply(BigDecimal.valueOf(orderItem.getQuantity())))) {
                throw new InConsistentProductPriceException("Total price not match");
            }
        }

    }

}

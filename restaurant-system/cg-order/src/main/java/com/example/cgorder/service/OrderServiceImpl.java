package com.example.cgorder.service;


import com.example.cgcommon.configuration.CacheClient;
import com.example.cgcommon.dto.response.OrderResponseDTO;
import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.dto.response.ProductStatusCacheDto;
import com.example.cgcommon.model.ProductStatus;
import com.example.cgcommon.request.OrderItemRequestDTO;
import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgcommon.request.ProductPricesRequestDto;


import com.example.cgorder.exception.InConsistentProductPriceException;
import com.example.cgorder.exception.OrderPayloadDeserializeException;
import com.example.cgorder.exception.OrderTooManyRequestException;
import com.example.cgorder.exception.ProductPriceNotFoundException;
import com.example.cgorder.feign.ProductFeignClient;
import com.example.cgorder.mapper.OrderItemMapper;
import com.example.cgorder.mapper.OrderMapper;
import com.example.cgorder.model.OrderIdempotent;
import com.example.cgorder.model.OrderOutbox;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderIdempotentRepository;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
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

    private final OrderIdempotentRepository idempotentRepository;

    private final OrderOutBoxService orderOutBoxService;

    private final ProductFeignClient productFeignClient;
    private final CacheClient cacheClient;

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(@NotNull PlaceOrderRequestDTO placeOrderRequestDTO, String idempotentKey) {
        Optional<OrderIdempotent> orderIdempotent = idempotentRepository.findByKey(idempotentKey);

        if(orderIdempotent.isPresent()){
            throw new OrderTooManyRequestException("Too Many Request");
        }


        validateProductPrice(placeOrderRequestDTO.getBranchId(), placeOrderRequestDTO.getOrderItems());
        validateOrderStatus(placeOrderRequestDTO.getOrderItems());

        var order = orderMapper.convertOrderFromPlaceOrderRequestDTO(placeOrderRequestDTO);

        var orderItemList = placeOrderRequestDTO.getOrderItems().stream()
                .map( orderItemMapper::convertOrderItemFromOrderItemRequestDTO).toList();

        order.setOrderItems(orderItemList);
        order.setOrderStatus(OrderStatus.RECEIVED);


        OrderOutbox orderOutbox;
        try {
            orderOutbox = OrderOutbox.builder()
                    .orderPayload(objectMapper.writeValueAsString(order))
                    .orderOutboxId(order.getOrderId())
                    .build();
            orderOutBoxService.saveOrderOutbox(orderOutbox);
        } catch (JsonProcessingException e) {
            throw new OrderPayloadDeserializeException(e.getMessage());
        }
        producerService.sendMessage(order);

        orderOutBoxService.deleteOrderOutbox(orderOutbox.getOrderOutboxId());

        return orderMapper.convertPlaceOrderRequestDTOFromOrder(orderRepository.save(order));
    }

    public String createIdempotentKey() {
        OrderIdempotent orderIdempotent = OrderIdempotent.builder()
                .key(UUID.randomUUID().toString())
                .build();

        Optional<OrderIdempotent> orderIdempotentDb = idempotentRepository.findByKey(orderIdempotent.getKey());

       if(orderIdempotentDb.isEmpty()) {
           idempotentRepository.save(orderIdempotent);
           return orderIdempotent.getKey();
        }
        return orderIdempotentDb.get().getKey();
    };

    public void validateProductPrice(UUID branchId, List<OrderItemRequestDTO> orderItems) {
        ProductPricesRequestDto productPricesRequestDto = ProductPricesRequestDto.builder()
                .productIds(orderItems.stream().map(OrderItemRequestDTO::getProductId).toList())
                .build();

        List<ProductPriceResponseDto>  productPrices = productFeignClient.getProductPrices(branchId,productPricesRequestDto);


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
    public void validateOrderStatus(List<OrderItemRequestDTO> orderItems) {
     List<ProductStatusCacheDto> cacheStatus = orderItems.stream().map(orderItemRequestDTO ->
             (ProductStatusCacheDto) cacheClient.get("status"))
             .toList();

        for (ProductStatusCacheDto status : cacheStatus) {
            if(status.getProductStatus().equals(ProductStatus.PASSIVE))
                throw new ProductPriceNotFoundException("Products status passive");
        }



    }

}

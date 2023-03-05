package com.example.cgrestaurant.dto.request.order;

import com.example.cgcommon.model.CardInfoDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PlaceOrderRequestDTO {
    private List<OrderItemRequestDTO> orderItems;

    private BigDecimal totalPrice;


    private CardInfoDto cardInfo;

    private UUID branchId;
}

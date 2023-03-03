package com.example.cgcommon.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderRequestDTO {
    private List<OrderItemRequestDTO> orderItems;

    private BigDecimal totalPrice;
}

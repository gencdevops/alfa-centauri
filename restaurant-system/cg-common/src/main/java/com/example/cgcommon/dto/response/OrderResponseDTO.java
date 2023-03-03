package com.example.cgcommon.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {
    private List<OrderItemResponseDTO> orderItems;

    private BigDecimal totalPrice;
}

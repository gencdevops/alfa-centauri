package com.example.cgcommon.dto.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO implements Serializable {
    private List<OrderItemResponseDTO> orderItems;

    private BigDecimal totalPrice;
}

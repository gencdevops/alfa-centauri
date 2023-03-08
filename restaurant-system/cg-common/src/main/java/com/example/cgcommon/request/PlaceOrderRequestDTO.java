package com.example.cgcommon.request;

import com.example.cgcommon.model.CardInfoDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderRequestDTO implements Serializable {
    @NotNull
    private List<OrderItemRequestDTO> orderItems;

    @NotNull(message = "Total price field is mandatory")
    @PositiveOrZero(message = "Total price field must be positive or zero")
    private BigDecimal totalPrice;


    @NotNull(message = "Card info field is mandatory")
    private CardInfoDto cardInfo;

    @NotNull
    private UUID branchId;
}


package com.example.cgorder.dto;

import com.example.cgcommon.model.CardInfoDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PlaceOrderRequestDTO {
   @NotNull
    private List<OrderItemRequestDTO> orderItems;

    @NotNull(message = "Total price field is mandatory")
    @PositiveOrZero(message = "Total price field must be positive or zero")
    private BigDecimal totalPrice;


    @NotNull(message = "Card info field is mandatory")
    private CardInfoDto cardInfo;
}

package com.example.cgorder.payment;

import com.example.cgorder.dto.OrderItemRequestDTO;
import com.example.cgorder.model.Card;

import java.util.List;

public interface PaymentSystem {
    Object pay(List<OrderItemRequestDTO> orderItemRequestDTOList, Card card);
}
package com.example.cgorder.adapter.payment;

import com.example.cgorder.client.OrderItemRequestDTO;
import com.example.cgorder.model.Card;

import java.util.List;

public interface PaymentSystem {
    Object pay(List<OrderItemRequestDTO> orderItemRequestDTOList, Card card);
}

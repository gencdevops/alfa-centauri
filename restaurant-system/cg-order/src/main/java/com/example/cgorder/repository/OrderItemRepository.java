package com.example.cgorder.repository;

import com.example.cgorder.model.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}

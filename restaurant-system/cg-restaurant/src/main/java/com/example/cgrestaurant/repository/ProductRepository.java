package com.example.cgrestaurant.repository;

import com.example.cgrestaurant.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByProductName(String productName);
}

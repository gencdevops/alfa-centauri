package com.example.cgrestaurant.repository;

import com.example.cgrestaurant.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}

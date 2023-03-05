package com.example.cgrestaurant.repository;

import com.example.cgrestaurant.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, UUID> {
    Optional<ProductPrice> getByProductIdAndBranchId(UUID productId, UUID branchID);

    Optional<ProductPrice> getByProductIdAndBranchIdIsNull(UUID productId);

    List<ProductPrice> getByBranchIdAndProductIdIn(UUID branchId, List<UUID> productIds);

    List<ProductPrice> getByProductIdInAndBranchIdIsNull(List<UUID> productIds);
}

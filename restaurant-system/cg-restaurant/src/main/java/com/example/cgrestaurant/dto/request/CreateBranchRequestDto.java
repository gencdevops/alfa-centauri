package com.example.cgrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateBranchRequestDto(
        @NotNull(message = "Branch name cannot be null")
        String branchName,
        @Nullable
        @PositiveOrZero(message = "Branch price field must be positive or zero")
        BigDecimal branchPrice,

        @NotNull(message = "Supplier id name cannot be null")
        UUID supplierId) {
}

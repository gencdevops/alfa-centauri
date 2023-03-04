package com.example.cgrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateBranchRequestDto(
        @NotNull(message = "Branch name cannot be null")
        String branchName,
        @NotNull(message = "Supplier id name cannot be null")
        UUID supplierId) {
}

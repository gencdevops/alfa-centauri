package com.example.cgrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateBranchRequestDto(
        @NotNull(message = "Branch name cannot be null")
        String branchName) {
}

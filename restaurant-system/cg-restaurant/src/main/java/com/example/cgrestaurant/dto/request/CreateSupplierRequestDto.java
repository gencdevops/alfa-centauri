package com.example.cgrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateSupplierRequestDto(
        @NotNull(message = "Supplier name cannot be null")
        @Size(min = 1, max = 200, message
                = "About Me must be between 1 and 200 characters")
        String supplierName) {
}

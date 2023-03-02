package com.example.cgrestaurant.dto.request;

import lombok.Builder;

@Builder
public record CreateSupplierRequest(String supplierName) {
}

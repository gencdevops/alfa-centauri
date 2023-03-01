package com.example.cgrestaurant.dto.request;

import java.util.UUID;

public record CreateBranchRequest(String branchName, UUID supplierId) {
}

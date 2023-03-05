package com.example.cgcommon.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPriceResponseDto(
        UUID branchId,
        UUID productId,
        BigDecimal price) {
}

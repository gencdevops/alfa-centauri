package com.example.cgrestaurant.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
//@Entity(name = "PRODUCT_PRICE")
public class ProductPrice {

    private UUID productId;

    private UUID branchId;

    private BigDecimal productPrice;

}

package com.example.cgrestaurant.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
//@Entity(name = "PRODUCT_PRICE")
public class ProductPrice {

    private Long productId;

    private Long branchId;

    private BigDecimal productPrice;

}

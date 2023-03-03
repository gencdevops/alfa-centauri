package com.example.cgrestaurant.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Embeddable
//@Entity(name = "PRODUCT_PRICE")\@EntityView(User.class)

public class ProductPrice {

//    @ManyToOne
//    @JoinColumn(name = "prodcut_id")
    private Product productId;

//    @ManyToOne
//    @JoinColumn(name = "branch_id")
    private Branch branchId;

    private BigDecimal productPrice;

}

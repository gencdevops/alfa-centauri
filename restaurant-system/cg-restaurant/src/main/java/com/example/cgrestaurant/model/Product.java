package com.example.cgrestaurant.model;

import com.example.cgrestaurant.model.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
//@Entity(name = "PRODUCTS")
public class Product {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private BigDecimal defaultPrice;

    private Long supplierId;

    private LocalDate createDate;

    private LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}

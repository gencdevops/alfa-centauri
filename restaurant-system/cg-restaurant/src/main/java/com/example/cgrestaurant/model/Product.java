package com.example.cgrestaurant.model;

import com.example.cgrestaurant.model.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Entity(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    private String productName;

    private BigDecimal defaultPrice;

    private UUID supplierId;

    private LocalDate createDate;

    private LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
}

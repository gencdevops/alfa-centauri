package com.example.cgrestaurant.model;

import com.example.cgrestaurant.model.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity(name = "PRODUCTS")
public class Product {

    @Id
    private UUID productId;

    @NotNull
    private String productName;

    private BigDecimal defaultPrice;

    private UUID supplierId;

    private LocalDate createDate;

    private LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}

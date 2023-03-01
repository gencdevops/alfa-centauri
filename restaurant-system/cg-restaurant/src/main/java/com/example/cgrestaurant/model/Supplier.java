package com.example.cgrestaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity(name = "SUPPLIERS")
public class Supplier {

// SHAYA

    @Id
    private UUID supplierId;

    private String supplierName;

    private LocalDate createDate;

    private LocalDate updateDate;
}

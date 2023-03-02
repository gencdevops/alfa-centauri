package com.example.cgrestaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Entity(name = "suppliers")
public class Supplier {

// SHAYA

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID supplierId;

    private String supplierName;

    private LocalDate createDate;

    private LocalDate updateDate;
}

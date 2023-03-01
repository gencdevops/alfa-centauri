package com.example.cgrestaurant.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
//@Entity(name = "suppliers")
public class Supplier {

// SHAYA

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String supplierName;

    private LocalDate createDate;

    private LocalDate updateDate;
}

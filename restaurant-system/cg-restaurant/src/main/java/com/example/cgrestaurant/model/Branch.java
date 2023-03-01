package com.example.cgrestaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Entity(name = "BRANCHES")
public class Branch {

    // Starbucks
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID branchId;

    private String branchName;

    private LocalDate createDate;

    private LocalDate updateDate;

    private UUID supplierId;
}

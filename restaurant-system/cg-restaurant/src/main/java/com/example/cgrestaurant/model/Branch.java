package com.example.cgrestaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity(name = "BRANCHES")
public class Branch {

    // Starbucks
    @Id
    private UUID branchId;

    @NotNull
    private String branchName;

    private LocalDate createDate;

    private LocalDate updateDate;

    @ManyToOne
    private Supplier supplierId;
}

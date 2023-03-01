package com.example.cgrestaurant.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
//@Entity(name = "BRANCHES")
public class Branch {

    // Starbucks
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    private String branchName;

    private LocalDate createDate;

    private LocalDate updateDate;


    private Long supplierId;
}

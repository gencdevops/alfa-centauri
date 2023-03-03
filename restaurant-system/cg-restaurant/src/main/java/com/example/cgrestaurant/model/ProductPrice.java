//package com.example.cgrestaurant.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Set;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Entity
//public class ProductPrice {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long productPriceId;
//
//
//
//    @OneToOne
//    @JoinColumn
//    private Product product;
//    @OneToOne
//    @JoinColumn
//    private Branch branch;
//
//    private BigDecimal productPrice;
//
//}

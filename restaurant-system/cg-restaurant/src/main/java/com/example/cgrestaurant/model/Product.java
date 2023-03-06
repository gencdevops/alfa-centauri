package com.example.cgrestaurant.model;

import com.example.cgcommon.model.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String productName;

    private UUID supplierId;



    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;


}

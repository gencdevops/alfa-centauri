package com.example.cgrestaurant.model;

import com.example.cgrestaurant.model.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private BigDecimal defaultPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplierId")
    private Supplier supplier;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime changeDayLastTime;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
}

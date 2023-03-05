package com.example.cgrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Branch {

    // Starbucks
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID branchId;

    @Column(unique=true)
    private String branchName;

    @Nullable
    private BigDecimal branchPrice;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @Column(columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime changeDayLastTime;


    private UUID supplierId;


}

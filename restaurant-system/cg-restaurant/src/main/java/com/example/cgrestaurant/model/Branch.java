package com.example.cgrestaurant.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Entity(name = "BRANCHES")
public class Branch {

    // Starbucks
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID branchId;

    private String branchName;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime changeDayLastTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplierId")
    private Supplier supplier;
}

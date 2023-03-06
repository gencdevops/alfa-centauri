package com.example.cgorder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderIdempotent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

  private String key;

}


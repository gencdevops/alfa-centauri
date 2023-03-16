package com.example.cgorder.model;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "order_outbox")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderOutboxId;
    @Lob
    private String orderPayload;
    private String paymentId;



}


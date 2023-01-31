package com.example.kafkalogbackxmlv2.model.entity;

import com.example.kafkalogbackxmlv2.model.enums.Currency;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    private Long receiverId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}

package com.example.kafkalogbackxmlv2.model.entity;

import com.example.kafkalogbackxmlv2.model.enums.Currency;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Account updateBalance(double amount) {
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
        return this;
    }
}

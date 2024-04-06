package org.imures.cashregister.cashregisters.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "cash_register_id")
    private CashRegister cashRegister;

}
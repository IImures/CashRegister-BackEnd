package org.imures.cashregister.cashregisters.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity(name = "cash_registers")
@Getter
@Setter
@RequiredArgsConstructor
public class CashRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String generalInfo;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "cashRegister", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Characteristic> characteristics;

}

package org.imures.cashregister.producer.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.product.entity.Product;

import java.util.Set;

@Entity(name = "producer")
@Getter
@Setter
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String producerName;

    @OneToMany(mappedBy = "producer")
    private Set<Product> products;

    @Column(nullable = false)
    private String edrpou;

}


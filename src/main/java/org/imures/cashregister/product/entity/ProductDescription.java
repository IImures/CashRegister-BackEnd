package org.imures.cashregister.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "product_description")
@Getter
@Setter
public class ProductDescription {

    @Id
    private Long id;

    @Column(nullable = false)
    private String description;

    @OneToOne(mappedBy = "productDescription")
    private Product product;
}

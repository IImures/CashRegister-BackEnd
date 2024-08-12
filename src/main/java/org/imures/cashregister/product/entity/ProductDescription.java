package org.imures.cashregister.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "product_description")
@Getter
@Setter
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String characteristics;

    @OneToOne(mappedBy = "productDescription")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="image_id")
    private ProductImage image;
}

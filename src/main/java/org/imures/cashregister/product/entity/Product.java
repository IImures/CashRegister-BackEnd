package org.imures.cashregister.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.catalog.entity.SubCatalog;
import org.imures.cashregister.producer.entity.Producer;

@Entity(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name= "sub_catalog_id", nullable = false)
    private SubCatalog subCatalog;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="image_id")
    private ProductImage image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_description_id")
    private ProductDescription productDescription;

    @ManyToOne
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

}



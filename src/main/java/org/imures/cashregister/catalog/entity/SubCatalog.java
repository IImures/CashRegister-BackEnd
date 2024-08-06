package org.imures.cashregister.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.product.entity.Product;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "sub_catalog")
@Setter
@Getter
public class SubCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subCatalogName;

    @ManyToOne
    @JoinColumn(name= "sub_category_type_fk", nullable = false)
    private SubCatalogType subCatalogType;

    @ManyToOne
    @JoinColumn(name = "catalog_fk")
    private Catalog catalog;

    @OneToMany(mappedBy = "subCatalog")
    private Set<Product> products = new HashSet<>();


}

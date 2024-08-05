package org.imures.cashregister.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "sub_catalog")
@Setter
@Getter
public class SubCatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subCatalogName;

    @ManyToOne
    @JoinColumn(name= "sub_category_type_fk", nullable = false)
    private SubCatalogTypeEntity subCatalogType;

    @ManyToOne
    @JoinColumn(name = "catalog_fk")
    private CatalogEntity catalog;

}

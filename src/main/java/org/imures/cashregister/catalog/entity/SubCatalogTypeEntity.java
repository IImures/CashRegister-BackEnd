package org.imures.cashregister.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity(name = "sub_catalog_type")
@Getter
@Setter
public class SubCatalogTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy ="subCatalogType")
    private Set<SubCatalogEntity> subCatalogs;
}

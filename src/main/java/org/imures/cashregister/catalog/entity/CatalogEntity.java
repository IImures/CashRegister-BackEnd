package org.imures.cashregister.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name ="catalog")
@Getter
@Setter
public class CatalogEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String catalogName;

    @OneToMany(mappedBy = "catalog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<SubCatalogEntity> subCatalogs = new HashSet<>();

    public void addSubCatalog(SubCatalogEntity subCatalogEntity){
        subCatalogs.add(subCatalogEntity);
        subCatalogEntity.setCatalog(this);
    }
}

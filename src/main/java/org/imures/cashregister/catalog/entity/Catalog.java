package org.imures.cashregister.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name ="catalog")
@Getter
@Setter
public class Catalog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String catalogName;

    @OneToMany(mappedBy = "catalog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<SubCatalog> subCatalogs = new HashSet<>();

    public void addSubCatalog(SubCatalog subCatalog){
        subCatalogs.add(subCatalog);
        subCatalog.setCatalog(this);
    }
}

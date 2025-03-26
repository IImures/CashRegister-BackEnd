package org.imures.cashregister.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<SubCatalog> subCatalogs = new ArrayList<>();

    public void addSubCatalog(SubCatalog subCatalog){
        subCatalogs.add(subCatalog);
        subCatalog.setCatalog(this);
    }
}

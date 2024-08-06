package org.imures.cashregister.catalog.repository;

import org.imures.cashregister.catalog.entity.SubCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCatalogRepository extends JpaRepository<SubCatalog, Long> {
    SubCatalog findBySubCatalogName(String subCatalogName);
}

package org.imures.cashregister.catalog.repository;

import org.imures.cashregister.catalog.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long>
{
    Catalog findByCatalogName(String name);

}

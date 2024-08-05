package org.imures.cashregister.catalog.repository;

import org.imures.cashregister.catalog.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogEntity, Integer>
{
    CatalogEntity findByCatalogName(String name);

}

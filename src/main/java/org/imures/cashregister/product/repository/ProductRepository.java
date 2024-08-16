package org.imures.cashregister.product.repository;

import org.imures.cashregister.catalog.entity.SubCatalog;
import org.imures.cashregister.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllBySubCatalog(Pageable pageable, SubCatalog subCatalog);

    Page<Product> findAllBySubCatalogAndProducer_IdIn(Pageable pageable, SubCatalog subCatalog, Set<Long> producerId);

    Page<Product> findAllBySubCatalogAndNameContainingIgnoreCase(Pageable pageable, SubCatalog subCatalog, String name);

    Page<Product> findAllBySubCatalogAndNameContainingIgnoreCaseAndProducer_IdIn(Pageable pageable, SubCatalog subCatalog, String name, Set<Long> producerId);
}

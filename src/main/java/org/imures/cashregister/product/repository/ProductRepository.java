package org.imures.cashregister.product.repository;

import org.imures.cashregister.catalog.entity.SubCatalog;
import org.imures.cashregister.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllBySubCatalog(Pageable pageable, SubCatalog subCatalog);
}

package org.imures.cashregister.product.repository;

import org.imures.cashregister.product.entity.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {
}

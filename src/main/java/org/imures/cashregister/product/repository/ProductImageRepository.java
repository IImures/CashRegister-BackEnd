package org.imures.cashregister.product.repository;

import org.imures.cashregister.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByNameAndType(String name, String type);
}

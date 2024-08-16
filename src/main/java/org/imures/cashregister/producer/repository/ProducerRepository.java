package org.imures.cashregister.producer.repository;

import org.imures.cashregister.producer.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    List<Producer> findByProducts_SubCatalog_Id(Long id);

}

package org.imures.cashregister.cashregisters.repository;

import org.imures.cashregister.cashregisters.entity.Item;
import org.imures.cashregister.type.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>{

}

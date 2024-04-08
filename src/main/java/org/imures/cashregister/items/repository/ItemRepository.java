package org.imures.cashregister.items.repository;

import org.imures.cashregister.items.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{

}

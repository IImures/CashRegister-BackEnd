package org.imures.cashregister.Items.repository;

import org.imures.cashregister.Items.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{

}

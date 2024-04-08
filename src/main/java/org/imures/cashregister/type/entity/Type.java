package org.imures.cashregister.type.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.Items.entity.Item;

import java.util.Set;

@Entity(name = "item_types")
@Getter
@Setter
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items;

}

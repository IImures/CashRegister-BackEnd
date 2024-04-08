package org.imures.cashregister.cashregisters.entity;

import jakarta.persistence.*;
import lombok.*;
import org.imures.cashregister.type.entity.Type;

import java.util.Set;


@Entity(name = "items")
@Getter
@Setter
@RequiredArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
//    private String type;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @Column(nullable = false)
    private String generalInfo;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Characteristic> characteristics;

}

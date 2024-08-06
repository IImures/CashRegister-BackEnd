package org.imures.cashregister.product.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "product_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(nullable = false)
    private byte[] imageData;

    @OneToOne(mappedBy = "image")
    private Product product;

}

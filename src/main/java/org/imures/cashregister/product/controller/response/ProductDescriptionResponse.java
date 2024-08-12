package org.imures.cashregister.product.controller.response;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDescriptionResponse {
    private Long id;
    private String productName;
    private String title;
    private String description;
    private String characteristics;
}

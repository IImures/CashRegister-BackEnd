package org.imures.cashregister.product.controller.response;

import lombok.*;
import org.imures.cashregister.catalog.controller.response.SubCatalogResponse;
import org.imures.cashregister.producer.controller.response.ProducerResponse;

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
    private ProducerResponse producer;
    private SubCatalogResponse subCatalog;
}

package org.imures.cashregister.product.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.imures.cashregister.catalog.controller.response.SubCatalogResponse;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private SubCatalogResponse subCatalog;
}

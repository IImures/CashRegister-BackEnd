package org.imures.cashregister.product.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String productName;

    private Long subCatalogId;
}

package org.imures.cashregister.product.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Product name is missing")
    private String productName;
    @NotNull(message = "Product must be associated with some sub-category")
    private Long subCatalogId;
}

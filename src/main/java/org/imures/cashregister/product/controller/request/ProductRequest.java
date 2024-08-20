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
    @NotNull(message = "Producer must be associated with product")
    private Long producerId;
    @NotBlank(message = "Product title is missing")
    private String title;
    @NotBlank(message = "Product description is missing")
    private String description;
    @NotBlank(message = "Product characteristics is missing")
    private String characteristics;
}

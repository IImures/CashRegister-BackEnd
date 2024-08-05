package org.imures.cashregister.catalog.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogRequest {
    @NotBlank(message = "Catalog Name cant be blank")
    private String catalogName;
    private SubCatalogRequest[] subCatalogs;
}

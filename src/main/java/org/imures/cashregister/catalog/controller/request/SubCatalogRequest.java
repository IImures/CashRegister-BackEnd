package org.imures.cashregister.catalog.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCatalogRequest {
    @NotNull(message = "Sub Catalog Name is missing")
    @NotBlank(message = "Sub Catalog Name cant be blank")
    private String subCatalogName;
    @NotNull(message = "Catalog type is missing")
    private Long subCatalogType;
}

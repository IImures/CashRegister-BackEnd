package org.imures.cashregister.catalog.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubCatalogRequest {
    @NotBlank(message = "Sub Catalog Name cant be blank")
    private String subCatalogName;
    @NotNull(message = "Sub Catalog type is missing")
    private Long subCatalogType;
    @NotNull(message = "Catalog id cant be null")
    private Long catalogId;
}

package org.imures.cashregister.catalog.controller.response;

import lombok.Data;
import org.imures.cashregister.catalog.entity.SubCatalogType;

@Data
public class SubCatalogResponse {
    private Long id;
    private String subCatalogName;
    private String subCatalogType;

    public void setSubCatalogType(SubCatalogType subCatalogType) {
        this.subCatalogType = subCatalogType.getType();
    }
}

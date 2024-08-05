package org.imures.cashregister.catalog.controller.response;

import lombok.Data;
import org.imures.cashregister.catalog.entity.SubCatalogTypeEntity;

@Data
public class SubCatalogResponse {
    private Long id;
    private String subCatalogName;
    private String subCatalogType;

    public void setSubCatalogType(SubCatalogTypeEntity subCatalogType) {
        this.subCatalogType = subCatalogType.getType();
    }
}

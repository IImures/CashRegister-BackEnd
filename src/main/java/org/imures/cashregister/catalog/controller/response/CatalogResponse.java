package org.imures.cashregister.catalog.controller.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
@Data
public class CatalogResponse {

    private Long id;
    private String catalogName;
    private Set<SubCatalogResponse> subCatalogs;
}

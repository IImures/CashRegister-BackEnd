package org.imures.cashregister.catalog.mapper;

import org.imures.cashregister.catalog.controller.response.SubCatalogResponse;
import org.imures.cashregister.catalog.entity.SubCatalogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubCatalogMapper {
    SubCatalogResponse fromEntityToResponse(SubCatalogEntity subCatalogEntity);
}

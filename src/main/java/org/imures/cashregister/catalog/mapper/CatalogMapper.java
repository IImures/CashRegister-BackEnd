package org.imures.cashregister.catalog.mapper;

import org.imures.cashregister.catalog.controller.response.CatalogResponse;
import org.imures.cashregister.catalog.entity.CatalogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

    CatalogResponse fromEntityToResponse(CatalogEntity catalog);

}

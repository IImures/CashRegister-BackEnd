package org.imures.cashregister.catalog.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.catalog.controller.request.CatalogRequest;
import org.imures.cashregister.catalog.controller.response.CatalogResponse;
import org.imures.cashregister.catalog.entity.CatalogEntity;
import org.imures.cashregister.catalog.entity.SubCatalogEntity;
import org.imures.cashregister.catalog.entity.SubCatalogTypeEntity;
import org.imures.cashregister.catalog.mapper.CatalogMapper;
import org.imures.cashregister.catalog.mapper.SubCatalogMapper;
import org.imures.cashregister.catalog.repository.CatalogRepository;
import org.imures.cashregister.catalog.repository.SubCatalogTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CatalogService
{

    private final SubCatalogMapper subCatalogMapper;
    private final CatalogMapper catalogMapper;

    private final SubCatalogTypeRepository subCatalogTypeRepository;
    private final CatalogRepository catalogRepository;

    @Transactional(readOnly = true)
    public Page<CatalogResponse> findAll(Pageable pageRequest) {
        Page<CatalogEntity> catalogPage = catalogRepository.findAll(pageRequest);

        Page<CatalogResponse> response = catalogPage.map(catalogMapper::fromEntityToResponse);
        response.forEach(subC -> subC.setSubCatalogs(
                catalogPage.get()
                        .filter( c -> Objects.equals(subC.getId(), c.getId()))
                        .flatMap(e -> e.getSubCatalogs().stream())
                        .map(subCatalogMapper::fromEntityToResponse).collect(Collectors.toSet())
        ));
        return response;
    }

    @Transactional(readOnly = true)
    public CatalogResponse getCatalogById(long catalogId) {
        CatalogEntity catalogEntity = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new EntityNotFoundException("Catalog not found"));

        CatalogResponse catalogResponse = catalogMapper.fromEntityToResponse(catalogEntity);
        catalogResponse.setSubCatalogs(
                catalogEntity.getSubCatalogs().stream()
                        .map(subCatalogMapper::fromEntityToResponse)
                        .collect(Collectors.toSet())
        );

        return catalogResponse;
    }

    @Transactional
    public CatalogResponse createCatalog(CatalogRequest catalogRequest) {
        CatalogEntity catalogEntity = catalogRepository.findByCatalogName(
                catalogRequest.getCatalogName()
        );
        if (catalogEntity != null) {
            throw new EntityExistsException("Catalog already exists");
        }
        CatalogEntity created = new CatalogEntity();
        created.setCatalogName(catalogRequest.getCatalogName());

        if(catalogRequest.getSubCatalogs() != null) {
            HashMap<Long, SubCatalogTypeEntity> types = subCatalogTypeRepository.findAll().stream()
                    .collect(Collectors.toMap(
                            SubCatalogTypeEntity::getId,
                            entity -> entity,
                            (existing, replacement) -> existing,
                            HashMap::new
                    ));
            Arrays.stream(catalogRequest.getSubCatalogs())
                    .map(o ->{
                        SubCatalogEntity subCat = new SubCatalogEntity();
                        subCat.setSubCatalogName(o.getSubCatalogName());
                        subCat.setSubCatalogType(types.get(o.getSubCatalogType()));
                        return subCat;
                    }).forEach(created::addSubCatalog);
        }

        CatalogEntity saved = catalogRepository.save(created);

        CatalogResponse response = catalogMapper.fromEntityToResponse(saved);
        response.setSubCatalogs(saved.getSubCatalogs().stream().map(subCatalogMapper::fromEntityToResponse).collect(Collectors.toSet()));
        return response;
    }


    @Transactional
    public void deleteCatalog(long catalogId) {
        CatalogEntity catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new EntityNotFoundException("Catalog not found"));

        catalogRepository.delete(catalog);
    }
}

package org.imures.cashregister.catalog.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.catalog.controller.request.CatalogRequest;
import org.imures.cashregister.catalog.controller.response.CatalogResponse;
import org.imures.cashregister.catalog.entity.Catalog;
import org.imures.cashregister.catalog.entity.SubCatalog;
import org.imures.cashregister.catalog.entity.SubCatalogType;
import org.imures.cashregister.catalog.mapper.CatalogMapper;
import org.imures.cashregister.catalog.mapper.SubCatalogMapper;
import org.imures.cashregister.catalog.repository.CatalogRepository;
import org.imures.cashregister.catalog.repository.SubCatalogTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        Page<Catalog> catalogPage = catalogRepository.findAll(pageRequest);

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
        Catalog catalog = getCatalogEntity(catalogId);

        CatalogResponse catalogResponse = catalogMapper.fromEntityToResponse(catalog);
        catalogResponse.setSubCatalogs(
                catalog.getSubCatalogs().stream()
                        .map(subCatalogMapper::fromEntityToResponse)
                        .collect(Collectors.toSet())
        );

        return catalogResponse;
    }

    @Transactional
    public CatalogResponse createCatalog(CatalogRequest catalogRequest) {
        Catalog catalog = catalogRepository.findByCatalogName(
                catalogRequest.getCatalogName()
        );
        if (catalog != null) {
            throw new EntityExistsException("Catalog already exists");
        }
        Catalog created = new Catalog();
        created.setCatalogName(catalogRequest.getCatalogName());

        if(catalogRequest.getSubCatalogs() != null) {
            HashMap<Long, SubCatalogType> types = subCatalogTypeRepository.findAll().stream()
                    .collect(Collectors.toMap(
                            SubCatalogType::getId,
                            entity -> entity,
                            (existing, replacement) -> existing,
                            HashMap::new
                    ));
            Arrays.stream(catalogRequest.getSubCatalogs())
                    .map(o ->{
                        SubCatalog subCat = new SubCatalog();
                        subCat.setSubCatalogName(o.getSubCatalogName());
                        subCat.setSubCatalogType(types.get(o.getSubCatalogType()));
                        return subCat;
                    }).forEach(created::addSubCatalog);
        }

        Catalog saved = catalogRepository.save(created);

        CatalogResponse response = catalogMapper.fromEntityToResponse(saved);
        response.setSubCatalogs(saved.getSubCatalogs().stream().map(subCatalogMapper::fromEntityToResponse).collect(Collectors.toSet()));
        return response;
    }


    @Transactional
    public void deleteCatalog(long catalogId) {
        Catalog catalog = getCatalogEntity(catalogId);

        catalogRepository.delete(catalog);
    }

    @Transactional(readOnly = true)
    public List<CatalogResponse> getAllCatalogs() {
        List<Catalog> catalogs = catalogRepository.findAll(Sort.by("id"));
        return catalogs.stream()
                .map(catalogMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CatalogResponse updateCatalog(CatalogRequest request, Long catalogId) {
        Catalog catalog = getCatalogEntity(catalogId);

        Optional.ofNullable(request.getCatalogName()).ifPresent(catalog::setCatalogName);

        Catalog saved = catalogRepository.save(catalog);

        CatalogResponse response = catalogMapper.fromEntityToResponse(saved);
        response.setSubCatalogs(saved.getSubCatalogs().stream().map(subCatalogMapper::fromEntityToResponse).collect(Collectors.toSet()));
        return response;
    }

    private Catalog getCatalogEntity(Long catalogId) {
        return catalogRepository.findById(catalogId)
                .orElseThrow(() -> new EntityNotFoundException("Catalog with id " + catalogId + " not found"));
    }
}

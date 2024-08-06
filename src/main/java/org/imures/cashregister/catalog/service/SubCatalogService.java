package org.imures.cashregister.catalog.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.catalog.controller.request.CreateSubCatalogRequest;
import org.imures.cashregister.catalog.controller.response.CatalogResponse;
import org.imures.cashregister.catalog.entity.Catalog;
import org.imures.cashregister.catalog.entity.SubCatalog;
import org.imures.cashregister.catalog.entity.SubCatalogType;
import org.imures.cashregister.catalog.mapper.CatalogMapper;
import org.imures.cashregister.catalog.mapper.SubCatalogMapper;
import org.imures.cashregister.catalog.repository.CatalogRepository;
import org.imures.cashregister.catalog.repository.SubCatalogRepository;
import org.imures.cashregister.catalog.repository.SubCatalogTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCatalogService {

    private final CatalogMapper catalogMapper;
    private final SubCatalogMapper subCatalogMapper;

    private final CatalogRepository catalogRepository;
    private final SubCatalogTypeRepository typeRepository;
    private final SubCatalogRepository subCatalogRepository;

    @Transactional(readOnly = true)
    public CatalogResponse getSubCatalogById(long subCatalogId) {
        SubCatalog subCatalog = subCatalogRepository.findById(subCatalogId)
                .orElseThrow(() -> new EntityNotFoundException("Sub-Catalog Not Found"));

        Catalog catalog = subCatalog.getCatalog();

        CatalogResponse catalogResponse = catalogMapper.fromEntityToResponse(catalog);
        catalogResponse.setSubCatalogs(
                new HashSet<>(Collections.singleton(subCatalogMapper.fromEntityToResponse(subCatalog)))
        );

        return catalogResponse;
    }

    @Transactional
    public CatalogResponse createSubCatalog(CreateSubCatalogRequest subCatalogRequest) {
        SubCatalog subCatalog = subCatalogRepository
                .findBySubCatalogName(subCatalogRequest.getSubCatalogName());
        if(subCatalog != null) {
            throw new EntityExistsException("Sub-Catalog already exists");
        }
        Catalog catalog = catalogRepository.findById(subCatalogRequest.getCatalogId())
                .orElseThrow(() ->new EntityNotFoundException("Catalog not found with is: " + subCatalogRequest.getCatalogId()));
        SubCatalogType type = typeRepository.findById(subCatalogRequest.getSubCatalogType())
                .orElseThrow(()-> new EntityNotFoundException("Sub-Catalog type not found with is: " + subCatalogRequest.getSubCatalogType()));

        SubCatalog created = new SubCatalog();
        created.setSubCatalogName(subCatalogRequest.getSubCatalogName());
        created.setCatalog(catalog);
        created.setSubCatalogType(type);

        SubCatalog saved = subCatalogRepository.save(created);

        CatalogResponse catalogResponse = catalogMapper.fromEntityToResponse(saved.getCatalog());
        catalogResponse.setSubCatalogs(
                new HashSet<>(Collections.singleton(subCatalogMapper.fromEntityToResponse(saved)))
        );
        return catalogResponse;
    }

    @Transactional
    public void deleteSubCatalog(long subCatalogId) {
        SubCatalog subCatalog = subCatalogRepository.findById(subCatalogId)
                .orElseThrow(()-> new EntityNotFoundException("Sub-Catalog not found with is: " + subCatalogId));
        subCatalogRepository.delete(subCatalog);
    }

    @Transactional
    public CatalogResponse updateSubCatalog(long subCatalogId, CreateSubCatalogRequest subCatalogRequest) {
        SubCatalog subCatalog = subCatalogRepository.findById(subCatalogId)
                .orElseThrow(()-> new EntityNotFoundException("Sub-Catalog not found with is: " + subCatalogId));

        Optional.ofNullable(subCatalogRequest.getCatalogId()).ifPresent((catalogId) -> {
            Catalog catalog = catalogRepository.findById(catalogId)
                    .orElseThrow(()-> new EntityNotFoundException("Catalog not found with is: " + catalogId));
            subCatalog.setCatalog(catalog);
        });

        Optional.ofNullable(subCatalogRequest.getSubCatalogType()).ifPresent((typeId)->{
            SubCatalogType subCatalogType = typeRepository.findById(typeId)
                    .orElseThrow(()-> new EntityNotFoundException("Sub-Catalog type not found with is: " + typeId));
            subCatalog.setSubCatalogType(subCatalogType);
        });


        Optional.ofNullable(subCatalogRequest.getSubCatalogName()).ifPresent(subCatalog::setSubCatalogName);

        SubCatalog saved = subCatalogRepository.save(subCatalog);

        CatalogResponse response = catalogMapper.fromEntityToResponse(saved.getCatalog());
        response.setSubCatalogs(
                new HashSet<>(Collections.singleton(subCatalogMapper.fromEntityToResponse(saved)))
        );

        return response;
    }
}

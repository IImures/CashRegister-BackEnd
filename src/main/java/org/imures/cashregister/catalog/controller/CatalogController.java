package org.imures.cashregister.catalog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.catalog.controller.request.CatalogRequest;
import org.imures.cashregister.catalog.controller.response.CatalogResponse;
import org.imures.cashregister.catalog.service.CatalogService;
import org.imures.cashregister.exceptions.NullValueException;
import org.imures.cashregister.exceptions.TooManyEntitiesRequestedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/catalog")
public class CatalogController
{

    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<Page<CatalogResponse>> getPagedCatalogs(
        @RequestParam(
                value = "page",
                required = false,
                defaultValue = "0"
        ) int page,
        @RequestParam(
                value = "limit",
                required = false,
                defaultValue = "20"
        ) int limit,
        @RequestParam(
                value = "sort",
                required = false,
                defaultValue = "id"
        ) String sortBy
    )
    {
        if(limit > 150 || limit <= 0) throw new TooManyEntitiesRequestedException("Limit must be less then 150 or higher then 0");
        Pageable pageRequest = PageRequest.of(page, limit, Sort.by(sortBy));
        Page<CatalogResponse> response = catalogService.findAll(pageRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{catalogId}")
    public ResponseEntity<CatalogResponse> getCatalog(@PathVariable long catalogId)
    {
        return new ResponseEntity<>(catalogService.getCatalogById(catalogId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CatalogResponse> createCatalog(
            @RequestBody @Valid CatalogRequest catalogRequest, Errors errors
    )
    {
        if(errors.hasErrors()) throw new NullValueException(errors.getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(catalogService.createCatalog(catalogRequest), HttpStatus.CREATED);
    }

    @DeleteMapping(path= "/{catalogId}")
    public ResponseEntity<Void> deleteCatalog(
            @PathVariable long catalogId
    ){
        catalogService.deleteCatalog(catalogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package org.imures.cashregister.catalog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.catalog.controller.request.CreateSubCatalogRequest;
import org.imures.cashregister.catalog.controller.response.CatalogResponse;
import org.imures.cashregister.catalog.service.SubCatalogService;
import org.imures.cashregister.exceptions.NullValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/sub-catalog")
public class SubCatalogController {

    private final SubCatalogService subCatalogService;

    @GetMapping("/{subCatalogId}")
    public ResponseEntity<CatalogResponse> getSubCatalog(
            @PathVariable long subCatalogId
    ) {
        return new ResponseEntity<>(subCatalogService.getSubCatalogById(subCatalogId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CatalogResponse> createSubCatalog(
            @RequestBody @Valid CreateSubCatalogRequest subCatalogRequest, Errors errors
    ){
        if(errors.hasErrors()) throw new NullValueException(errors.getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(subCatalogService.createSubCatalog(subCatalogRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{subCatalogId}")
    public ResponseEntity<CatalogResponse> updateSubCatalog(
            @PathVariable long subCatalogId,
            @RequestBody CreateSubCatalogRequest subCatalogRequest
    ){
        return new ResponseEntity<>(subCatalogService.updateSubCatalog(subCatalogId, subCatalogRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{subCatalogId}")
    public ResponseEntity<Void> deleteSubCatalog(
            @PathVariable long subCatalogId
    ){
        subCatalogService.deleteSubCatalog(subCatalogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

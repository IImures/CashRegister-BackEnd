package org.imures.cashregister.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.exceptions.NullValueException;
import org.imures.cashregister.product.controller.request.ProductDescriptionRequest;
import org.imures.cashregister.product.controller.request.ProductRequest;
import org.imures.cashregister.product.controller.response.ProductDescriptionResponse;
import org.imures.cashregister.product.controller.response.ProductResponse;
import org.imures.cashregister.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    @GetMapping("subcatalog/{subCatalogId}")
    public ResponseEntity<Page<ProductResponse>> getPagedProducts(
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
            ) String sortBy,
            @RequestParam(
                    value ="producers",
                    required = false
            ) String producers,
            @RequestParam(
                    value = "name",
                    required = false
            ) String name,
            @PathVariable Long subCatalogId
    ){
        Pageable pageRequest = PageRequest.of(page, limit, Sort.by(sortBy));
        Page<ProductResponse> response;

        // If both producers and name are null or empty, retrieve all products in the sub-catalog
        if ((producers == null || producers.isEmpty()) && (name == null || name.isEmpty())) {
            response = productService.findAll(pageRequest, subCatalogId);
        }
        // If only producers is provided, filter by producer
        else if (producers != null && !producers.isEmpty() && (name == null || name.isEmpty())) {
            response = productService.findAllByProducer(pageRequest, subCatalogId, producers);
        }
        // If only name is provided, filter by name
        else if (producers == null || producers.isEmpty()) {
            response = productService.findAllByName(pageRequest, subCatalogId, name);
        }
        // If both producers and name are provided, filter by both
        else {
            response = productService.findAllByProducerAndName(pageRequest, subCatalogId, producers, name);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId
    ) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<ProductResponse> createProduct(
//            @RequestBody @Valid ProductRequest productRequest, Errors errors
//    ) {
//        if(errors.hasErrors()) throw new NullValueException(errors.getAllErrors().get(0).getDefaultMessage());
//        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid ProductRequest productRequest, Errors errors
    ) {
        if(errors.hasErrors()) throw new NullValueException(errors.getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "{productId}/image")
    public ResponseEntity<Void> addProductImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long productId
            ) throws IOException {
        productService.addProductImage(image, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{productId}/image")
    public ResponseEntity<String> getImage(
            @PathVariable Long productId
    ) {
        return getBase64Image(productService.getImageBytes(productId));
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(
            @PathVariable Long productId
    ){
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequest request
    ){
        return new ResponseEntity<>(productService.updateProduct(request, productId), HttpStatus.OK);
    }

    @GetMapping(path = "{id}/description")
    public ResponseEntity<ProductDescriptionResponse> getProductDescription(
            @PathVariable Long id
    ){
        return new ResponseEntity<>(productService.getProductDescription(id), HttpStatus.OK);
    }

//    @PostMapping(path = "{id}/description")
//    public ResponseEntity<ProductDescriptionResponse> createProductDescription(
//            @PathVariable Long id,
//            @RequestBody @Valid ProductDescriptionRequest request, Errors errors
//            ){
//        if(errors.hasErrors()) throw new NullValueException(errors.getAllErrors().get(0).getDefaultMessage());
//        return new ResponseEntity<>(productService.createProductDescription(id, request), HttpStatus.CREATED);
//    }

    @PutMapping(path = "{productId}/description/image")
    public ResponseEntity<Void> addProductDescriptionImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long productId
    ) throws IOException {
        productService.addProductDescriptionImage(image, productId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping(path = "{productId}/description")
    public ResponseEntity<ProductDescriptionResponse> updateProductDescription(
            @PathVariable Long productId,
            @RequestBody ProductDescriptionRequest request
    ){
        return new ResponseEntity<>(productService.updateProductDescription(request, productId), HttpStatus.OK);
    }

    @GetMapping(path = "{id}/description/image")
    public ResponseEntity<String> getProductDescriptionImage(
            @PathVariable Long id
    ){
        return getBase64Image(productService.getProductDescriptionImage(id));
    }


    private ResponseEntity<String> getBase64Image(byte[] image) {
        String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(base64Image);
    }
}

package org.imures.cashregister.product.controller;

import lombok.RequiredArgsConstructor;
import org.imures.cashregister.exceptions.TooManyEntitiesRequestedException;
import org.imures.cashregister.product.controller.request.ProductRequest;
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

    @GetMapping(    "sub-catalog/{subCatalogId}")
    public ResponseEntity<Page<ProductResponse>> getPagedCatalogs(
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
            @PathVariable Long subCatalogId
    ){
        if(limit > 150 || limit <= 0) throw new TooManyEntitiesRequestedException("Limit must be less then 150 or higher then 0");
        Pageable pageRequest = PageRequest.of(page, limit, Sort.by(sortBy));
        Page<ProductResponse> response = productService.findAll(pageRequest, subCatalogId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId
    ) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductRequest productRequest
    ) {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "{productId}/image")
    public ResponseEntity<ProductResponse> addProductImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long productId
            ) throws IOException {
        return new ResponseEntity<>(productService.addProductImage(image, productId), HttpStatus.OK);
    }

    @GetMapping(path = "{productId}/image")
    public ResponseEntity<String> getImage(
            @PathVariable Long productId
    ) {
        byte[] image = productService.getImageBytes(productId);
        String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(base64Image);
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(
            @PathVariable Long productId
    ){
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

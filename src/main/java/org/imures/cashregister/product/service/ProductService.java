package org.imures.cashregister.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.catalog.entity.SubCatalog;
import org.imures.cashregister.catalog.mapper.SubCatalogMapper;
import org.imures.cashregister.catalog.repository.SubCatalogRepository;
import org.imures.cashregister.product.controller.request.ProductRequest;
import org.imures.cashregister.product.controller.response.ProductResponse;
import org.imures.cashregister.product.entity.Product;
import org.imures.cashregister.product.entity.ProductImage;
import org.imures.cashregister.product.mapper.ProductMapper;
import org.imures.cashregister.product.repository.ProductImageRepository;
import org.imures.cashregister.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final SubCatalogMapper subCatalogMapper;

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final SubCatalogRepository subCatalogRepository;

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        ProductResponse productResponse = productMapper.fromEntityToResponse(product);
        productResponse.setSubCatalog(subCatalogMapper.fromEntityToResponse(product.getSubCatalog()));

        return productResponse;
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        SubCatalog subCatalog = subCatalogRepository.findById(productRequest.getSubCatalogId())
                .orElseThrow(()-> new EntityNotFoundException("SubCatalog with id " + productRequest.getSubCatalogId() + " not found"));

        Product createdProduct = new Product();
        createdProduct.setName(productRequest.getProductName());
        createdProduct.setSubCatalog(subCatalog);

        Product saved = productRepository.save(createdProduct);

        ProductResponse productResponse = productMapper.fromEntityToResponse(saved);
        productResponse.setSubCatalog(subCatalogMapper.fromEntityToResponse(saved.getSubCatalog()));

        return productResponse;
    }

    @Transactional
    public ProductResponse addProductImage(MultipartFile image, Long productId) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

//        Optional<ProductImage> optional=productImageRepository.findByNameAndType(image.getOriginalFilename(),image.getContentType());
        if (product.getImage() != null){
            ProductImage toDelete = product.getImage();

            ProductImage imageData = (
                    ProductImage.builder()
                            .name(image.getName())
                            .type(image.getContentType())
                            .imageData(image.getBytes())
                            .product(product)
                            .build()
            );
            product.setImage(imageData);

            productImageRepository.delete(toDelete);
        }else{
            ProductImage imageData = (
                    ProductImage.builder()
                            .name(image.getName())
                            .type(image.getContentType())
                            .imageData(image.getBytes())
                            .product(product)
                            .build()
            );
            product.setImage(imageData);
        }
        Product saved = productRepository.save(product);

        ProductResponse productResponse = productMapper.fromEntityToResponse(saved);
        productResponse.setSubCatalog(subCatalogMapper.fromEntityToResponse(saved.getSubCatalog()));

        return productResponse;
    }

    @Transactional(readOnly = true)
    public byte[] getImageBytes(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
        if (product.getImage() == null){
            return new byte[0];
        }

        return product.getImage().getImageData();
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageRequest, Long subCatalogId) {
        SubCatalog subCatalog = subCatalogRepository.findById(subCatalogId)
                .orElseThrow(() -> new EntityNotFoundException("SubCatalog with id " + subCatalogId + " not found"));

        Page<Product> productPage = productRepository.findAllBySubCatalog(pageRequest, subCatalog);

        Page<ProductResponse> response = productPage.map(productMapper::fromEntityToResponse);
        response.forEach( prd->
                 productPage.get()
                        .filter( c -> Objects.equals(prd.getId(), c.getId()))
                         .map(Product::getSubCatalog)
                         .map(subCatalogMapper::fromEntityToResponse)
                         .forEach(prd::setSubCatalog)
        );
        return response;
    }
}

package org.imures.cashregister.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.catalog.entity.SubCatalog;
import org.imures.cashregister.catalog.mapper.SubCatalogMapper;
import org.imures.cashregister.catalog.repository.SubCatalogRepository;
import org.imures.cashregister.producer.entity.Producer;
import org.imures.cashregister.producer.mapper.ProducerMapper;
import org.imures.cashregister.producer.repository.ProducerRepository;
import org.imures.cashregister.product.controller.request.ProductDescriptionRequest;
import org.imures.cashregister.product.controller.request.ProductRequest;
import org.imures.cashregister.product.controller.response.ProductDescriptionResponse;
import org.imures.cashregister.product.controller.response.ProductResponse;
import org.imures.cashregister.product.entity.Product;
import org.imures.cashregister.product.entity.ProductDescription;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final SubCatalogMapper subCatalogMapper;
    private final ProducerMapper producerMapper;

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final SubCatalogRepository subCatalogRepository;
    private final ProducerRepository producerRepository;

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        Product product = getProductEntity(productId);

        return getProductResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        SubCatalog subCatalog = getSubCatalog(productRequest.getSubCatalogId());

        Producer producer = producerRepository.findById(productRequest.getProducerId())
                .orElseThrow(() -> new EntityNotFoundException("Producer with id " + productRequest.getProducerId() + " not found"));

        Product createdProduct = new Product();
        createdProduct.setName(productRequest.getProductName());
        createdProduct.setSubCatalog(subCatalog);
        createdProduct.setProducer(producer);

        ProductDescription productDescription = new ProductDescription();
        productDescription.setTitle(productRequest.getTitle());
        productDescription.setDescription(productRequest.getDescription());
        productDescription.setCharacteristics(productRequest.getCharacteristics());

        createdProduct.setProductDescription(productDescription);

        Product saved = productRepository.save(createdProduct);

        return getProductResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(ProductRequest request, Long productId) {
        Product product = getProductEntity(productId);

        Optional.ofNullable(request.getProductName()).ifPresent(product::setName);

        Optional.ofNullable(request.getSubCatalogId()).ifPresent((subCatalogId)->{
            SubCatalog subCatalog = getSubCatalog(subCatalogId);
            product.setSubCatalog(subCatalog);
        });

        Optional.ofNullable(request.getProducerId()).ifPresent((producerId)->{
            Producer producer = producerRepository.findById(producerId)
                    .orElseThrow(()-> new EntityNotFoundException("Producer with id " + producerId + " not found"));
            product.setProducer(producer);
        });

        ProductDescription productDescription = product.getProductDescription();
        if (productDescription == null) {
            productDescription = new ProductDescription();
            productDescription.setTitle(request.getTitle());
            productDescription.setDescription(request.getDescription());
            productDescription.setCharacteristics(request.getCharacteristics());
        }else{
            Optional.ofNullable(request.getCharacteristics()).ifPresent(productDescription::setCharacteristics);
            Optional.ofNullable(request.getTitle()).ifPresent(productDescription::setTitle);
            Optional.ofNullable(request.getDescription()).ifPresent(productDescription::setDescription);
        }

        productRepository.save(product);

        return getProductResponse(product);
    }

    @Transactional
    public void addProductImage(MultipartFile image, Long productId) throws IOException {
        Product product = getProductEntity(productId);

        ProductImage imageData = ProductImage.builder()
                .name(image.getName())
                .type(image.getContentType())
                .imageData(image.getBytes())
                .build();

        imageData = productImageRepository.save(imageData);

        if (product.getImage() != null) {
            ProductImage toDelete = product.getImage();
            productImageRepository.delete(toDelete);
        }

        product.setImage(imageData);

        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public byte[] getImageBytes(Long productId) {
        Product product = getProductEntity(productId);
        if (product.getImage() == null){
            return new byte[0];
        }

        return product.getImage().getImageData();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProductEntity(productId);
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageRequest, Long subCatalogId) {
        SubCatalog subCatalog = getSubCatalog(subCatalogId);

        Page<Product> productPage = productRepository.findAllBySubCatalog(pageRequest, subCatalog);

        return getPagedProductResponse(productPage);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllByName(Pageable pageRequest, Long subCatalogId, String name) {
        SubCatalog subCatalog = getSubCatalog(subCatalogId);

        Page<Product> productPage = productRepository.findAllBySubCatalogAndNameContainingIgnoreCase(pageRequest, subCatalog, name);

        return getPagedProductResponse(productPage);
    }


    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllByProducer(Pageable pageRequest, Long subCatalogId, String producers) {
        SubCatalog subCatalog = getSubCatalog(subCatalogId);

        Set<Long> producersId = Arrays.stream(producers.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        Page<Product> productPage = productRepository.findAllBySubCatalogAndProducer_IdIn(pageRequest, subCatalog, producersId);
        return getPagedProductResponse(productPage);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllByProducerAndName(Pageable pageRequest, Long subCatalogId, String producers, String name) {
        SubCatalog subCatalog = getSubCatalog(subCatalogId);

        Set<Long> producersId = Arrays.stream(producers.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        Page<Product> productPage = productRepository.findAllBySubCatalogAndNameContainingIgnoreCaseAndProducer_IdIn(pageRequest, subCatalog, name, producersId);

        return getPagedProductResponse(productPage);
    }

    @Transactional(readOnly = true)
    public ProductDescriptionResponse getProductDescription(Long id) {
        Product product = getProductEntity(id);

        ProductDescription productDescription = getProductDescriptionEntity(id);

        return ProductDescriptionResponse.builder()
                .id(productDescription.getId())
                .productName(product.getName())
                .title(productDescription.getTitle())
                .description(productDescription.getDescription())
                .characteristics(productDescription.getCharacteristics())
                .producer(producerMapper.fromEntityToResponse(product.getProducer()))
                .subCatalog(subCatalogMapper.fromEntityToResponse(product.getSubCatalog()))
                .build();
    }


    @Transactional
    public void addProductDescriptionImage(MultipartFile image, Long productId) throws IOException {
        Product product = getProductEntity(productId);
        ProductDescription productDescription = getProductDescriptionEntity(productId);

        ProductImage imageData = ProductImage.builder()
                .name(image.getName())
                .type(image.getContentType())
                .imageData(image.getBytes())
                .build();

        imageData = productImageRepository.save(imageData);

        if (productDescription.getImage() != null) {
            ProductImage toDelete = productDescription.getImage();
            productDescription.setImage(null);
            productImageRepository.delete(toDelete);
        }

        productDescription.setImage(imageData);

        productRepository.save(product);
    }


    @Transactional(readOnly = true)
    public byte[] getProductDescriptionImage(Long id) {
        ProductDescription productDescription = getProductDescriptionEntity(id);

        if (productDescription.getImage() == null){
            return new byte[0];
        }

        return productDescription.getImage().getImageData();
    }

    @Transactional
    public ProductDescriptionResponse updateProductDescription(ProductDescriptionRequest request, Long productId) {
        ProductDescription productDescription = getProductDescriptionEntity(productId);

        Optional.ofNullable(request.getCharacteristics()).ifPresent(productDescription::setCharacteristics);
        Optional.ofNullable(request.getDescription()).ifPresent(productDescription::setDescription);
        Optional.ofNullable(request.getTitle()).ifPresent(productDescription::setTitle);

        Product savedProduct = productRepository.save(productDescription.getProduct());
        ProductDescription savedDescription = savedProduct.getProductDescription();

        return ProductDescriptionResponse.builder()
                .id(savedDescription.getId())
                .productName(savedProduct.getName())
                .title(savedDescription.getTitle())
                .description(savedDescription.getDescription())
                .characteristics(savedDescription.getCharacteristics())
                .build();
    }

    private SubCatalog getSubCatalog(Long subCatalogId) {
        return subCatalogRepository.findById(subCatalogId)
                .orElseThrow(() -> new EntityNotFoundException("SubCatalog with id " + subCatalogId + " not found"));
    }

    private Product getProductEntity(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
    }

    private ProductDescription getProductDescriptionEntity(Long productId) {
        ProductDescription productDescription = getProductEntity(productId).getProductDescription();
        if (productDescription == null) throw new EntityNotFoundException("Description of the product with id " + productId + " not found");
        return productDescription;
    }

    private ProductResponse getProductResponse(Product product) {
        ProductResponse productResponse = productMapper.fromEntityToResponse(product);
        productResponse.setSubCatalog(subCatalogMapper.fromEntityToResponse(product.getSubCatalog()));
        return productResponse;
    }

    private Page<ProductResponse> getPagedProductResponse(Page<Product> productPage) {
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

package org.imures.cashregister.product.mapper;

import org.imures.cashregister.product.controller.response.ProductResponse;
import org.imures.cashregister.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse fromEntityToResponse(Product product);

}

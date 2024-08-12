package org.imures.cashregister.product.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDescriptionRequest {
    @NotBlank(message = "Product title is missing")
    private String title;
    @NotBlank(message = "Product description is missing")
    private String description;
    @NotBlank(message = "Product characteristics is missing")
    private String characteristics;
}

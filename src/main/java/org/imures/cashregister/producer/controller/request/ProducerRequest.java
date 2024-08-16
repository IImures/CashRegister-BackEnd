package org.imures.cashregister.producer.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerRequest {
    @NotBlank(message = "Producer name is missing")
    private String producerName;
    @NotBlank(message = "Producer edrpou is missing")
    private String edrpou;
}

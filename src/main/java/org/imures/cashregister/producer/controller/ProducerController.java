package org.imures.cashregister.producer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.exceptions.NullValueException;
import org.imures.cashregister.producer.controller.request.ProducerRequest;
import org.imures.cashregister.producer.controller.response.ProducerResponse;
import org.imures.cashregister.producer.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/producer")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    public ResponseEntity<List<ProducerResponse>> getAllProducers() {
        return new ResponseEntity<>(producerService.getAllProducers(), HttpStatus.OK);
    }

    @GetMapping("{producerId}")
    public ResponseEntity<ProducerResponse> getProducer(
            @PathVariable Long producerId
    ) {
        return new ResponseEntity<>(producerService.getProducerById(producerId), HttpStatus.OK);
    }

    @GetMapping(path = "subcatalog/{subCatalogId}")
    public ResponseEntity<List<ProducerResponse>> getProducersBySubCategory(
            @PathVariable Long subCatalogId
    ){
        return new ResponseEntity<>(producerService.getAllProducersBySubCategory(subCatalogId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProducerResponse> createProducer(
            @RequestBody @Valid ProducerRequest producerRequest, Errors errors
    ){
        if(errors.hasErrors()) throw new NullValueException(errors.getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(producerService.createProducer(producerRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "{producerId}")
    public ResponseEntity<ProducerResponse> updateProducer(
            @RequestBody ProducerRequest producerRequest,
            @PathVariable Long producerId
    ){
        return new ResponseEntity<>(producerService.updateProducer(producerRequest, producerId), HttpStatus.OK);
    }

    @DeleteMapping(path = "{producerId}")
    public ResponseEntity<Void> deleteProducer(
            @PathVariable Long producerId
    ){
        producerService.deleteProducer(producerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

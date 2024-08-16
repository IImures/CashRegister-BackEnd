package org.imures.cashregister.producer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.imures.cashregister.producer.controller.request.ProducerRequest;
import org.imures.cashregister.producer.controller.response.ProducerResponse;
import org.imures.cashregister.producer.entity.Producer;
import org.imures.cashregister.producer.mapper.ProducerMapper;
import org.imures.cashregister.producer.repository.ProducerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final ProducerMapper producerMapper;

    @Transactional(readOnly = true)
    public List<ProducerResponse> getAllProducers() {
        return producerRepository.findAll().stream()
                .map(producerMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProducerResponse getProducerById(Long producerId) {
        Producer producer = producerRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Producer with id " + producerId + " not found"));

        return producerMapper.fromEntityToResponse(producer);
    }

    @Transactional(readOnly = true)
    public List<ProducerResponse> getAllProducersBySubCategory(Long subCatalogId) {
        List<Producer> producers = producerRepository.findByProducts_SubCatalog_Id(subCatalogId);
        return producers.stream()
                .map(producerMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProducerResponse createProducer(ProducerRequest producerRequest) {
        Producer producer = new Producer();
        producer.setProducerName(producerRequest.getProducerName());
        producer.setEdrpou(producerRequest.getEdrpou());

        return producerMapper.fromEntityToResponse(producerRepository.save(producer));
    }

    @Transactional
    public ProducerResponse updateProducer(ProducerRequest producerRequest, Long producerId) {
        Producer producer = producerRepository.findById(producerId)
                .orElseThrow(() -> new EntityNotFoundException("Producer with id " + producerId + " not found"));

        Optional.ofNullable(producerRequest.getEdrpou()).ifPresent(producer::setEdrpou);
        Optional.ofNullable(producerRequest.getProducerName()).ifPresent(producer::setProducerName);

        return producerMapper.fromEntityToResponse(producerRepository.save(producer));
    }

    @Transactional
    public void deleteProducer(Long producerId) {
        Producer producer = producerRepository.findById(producerId)
                .orElseThrow(() -> new EntityNotFoundException("Producer with id " + producerId + " not found"));
        producerRepository.delete(producer);
    }
}

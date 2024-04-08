package org.imures.cashregister.type.service;

import lombok.RequiredArgsConstructor;
import org.imures.cashregister.type.controller.reponse.TypeResponse;
import org.imures.cashregister.type.controller.request.TypeRequest;
import org.imures.cashregister.type.entity.Type;
import org.imures.cashregister.type.mapper.TypeMapper;
import org.imures.cashregister.type.repositroy.TypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;
    private final TypeMapper mapper;
    private static final String ENTITY_NOT_FOUND = "Item wasn't found with such id: ";

    @Transactional(readOnly = true)
    public Page<TypeResponse> getAllItems(Pageable pageRequest) {
        Page<Type> items = typeRepository.findAll(pageRequest);
        return items.map(mapper::fromEntityToResponse);
    }

    @Transactional(readOnly = false)
    public TypeResponse createType(TypeRequest typeRequest) {
        Type type = mapper.fromRequestToEntity(typeRequest);
        Type savedType = typeRepository.save(type);
        return mapper.fromEntityToResponse(savedType);
    }

    @Transactional(readOnly = true)
    public TypeResponse getType(Long id) {
        Type item = typeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ENTITY_NOT_FOUND + id)
        );
        return mapper.fromEntityToResponse(item);
    }

    public void deleteType(Long id) {
        Type type = typeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ENTITY_NOT_FOUND + id)
        );
        typeRepository.delete(type);
    }
}

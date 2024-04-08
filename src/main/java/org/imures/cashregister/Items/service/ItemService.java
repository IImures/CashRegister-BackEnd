package org.imures.cashregister.Items.service;

import lombok.RequiredArgsConstructor;
import org.imures.cashregister.Items.controller.request.ItemRequest;
import org.imures.cashregister.Items.controller.response.ItemResponse;
import org.imures.cashregister.Items.entity.Characteristic;
import org.imures.cashregister.Items.entity.Item;
import org.imures.cashregister.Items.mapper.ItemMapper;
import org.imures.cashregister.Items.repository.ItemRepository;
import org.imures.cashregister.type.entity.Type;
import org.imures.cashregister.type.repositroy.TypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final TypeRepository typeRepository;
    private final ItemMapper mapper;
    private static final String ENTITY_NOT_FOUND = "Item wasn't found with such id: ";

    @Transactional(readOnly = true)
    public Page<ItemResponse> getAllItems(Pageable pageRequest) {
        Page<Item> items = itemRepository.findAll(pageRequest);
        return items.map(mapper::fromEntityToResponse);
    }
    
    @Transactional(readOnly = false)
    public ItemResponse createItem(ItemRequest item) {
        //Item newItem = mapper.fromRequestToEntity(item);
        Item newItem = new Item();

        BeanUtils.copyProperties(item, newItem);

        Type type = typeRepository.findByType(item.getTypeName()).orElseThrow(
                () -> new IllegalArgumentException("Type wasn't found with such name: " + item.getTypeName())
        );
        newItem.setType(type);

        for(Characteristic characteristic : newItem.getCharacteristics()){
            characteristic.setItem(newItem);
        }

        Item savedItem = itemRepository.save(newItem);
        
        return mapper.fromEntityToResponse(savedItem);
    }

    @Transactional(readOnly = true)
    public ItemResponse getItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ENTITY_NOT_FOUND + id)
        );
        return mapper.fromEntityToResponse(item);
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ENTITY_NOT_FOUND + id)
        );
        item.setType(null);
        itemRepository.delete(item);
    }
}

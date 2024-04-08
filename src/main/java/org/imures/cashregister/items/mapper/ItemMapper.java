package org.imures.cashregister.items.mapper;

import org.imures.cashregister.items.controller.request.ItemRequest;
import org.imures.cashregister.items.controller.response.ItemResponse;
import org.imures.cashregister.items.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponse fromEntityToResponse(Item item);

    Item fromRequestToEntity(ItemRequest request);
}

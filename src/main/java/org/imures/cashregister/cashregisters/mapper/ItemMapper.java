package org.imures.cashregister.cashregisters.mapper;

import org.imures.cashregister.cashregisters.controller.request.ItemRequest;
import org.imures.cashregister.cashregisters.controller.response.ItemResponse;
import org.imures.cashregister.cashregisters.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponse fromEntityToResponse(Item item);

    Item fromRequestToEntity(ItemRequest request);
}

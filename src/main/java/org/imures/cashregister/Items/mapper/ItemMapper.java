package org.imures.cashregister.Items.mapper;

import org.imures.cashregister.Items.controller.request.ItemRequest;
import org.imures.cashregister.Items.controller.response.ItemResponse;
import org.imures.cashregister.Items.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponse fromEntityToResponse(Item item);

    Item fromRequestToEntity(ItemRequest request);
}

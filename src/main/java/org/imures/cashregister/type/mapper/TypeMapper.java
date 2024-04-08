package org.imures.cashregister.type.mapper;


import org.imures.cashregister.type.controller.reponse.TypeResponse;
import org.imures.cashregister.type.controller.request.TypeRequest;
import org.imures.cashregister.type.entity.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeResponse fromEntityToResponse(Type type);

    Type fromRequestToEntity(TypeRequest request);

}

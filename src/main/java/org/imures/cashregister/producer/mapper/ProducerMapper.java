package org.imures.cashregister.producer.mapper;

import org.imures.cashregister.producer.controller.response.ProducerResponse;
import org.imures.cashregister.producer.entity.Producer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProducerMapper {

    ProducerResponse fromEntityToResponse(Producer producer);

}

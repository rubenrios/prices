package com.rubenrbr.prices.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.infrastructure.persistence.entity.PriceEntity;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  Price toDomain(PriceEntity entity);
}

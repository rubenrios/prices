package com.rubenrbr.prices.infrastructure.rest.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.infrastructure.rest.dto.PriceResponse;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

  @Mapping(source = "startDate", target = "startDate", qualifiedByName = "toOffsetDateTime")
  @Mapping(source = "endDate", target = "endDate", qualifiedByName = "toOffsetDateTime")
  PriceResponse toResponse(Price domain);

  @org.mapstruct.Named("toOffsetDateTime")
  default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
    return localDateTime != null
        ? localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime()
        : null;
  }
}

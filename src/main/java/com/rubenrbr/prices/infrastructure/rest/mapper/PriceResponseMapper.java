package com.rubenrbr.prices.infrastructure.rest.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.infrastructure.rest.dto.PriceResponse;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

  @Mapping(source = "startDate", target = "startDate", qualifiedByName = "toStringDateTime")
  @Mapping(source = "endDate", target = "endDate", qualifiedByName = "toStringDateTime")
  PriceResponse toResponse(Price domain);

  @Named("toStringDateTime")
  default String toStringDateTime(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
  }

  @Named("toDateTime")
  default LocalDateTime toDateTime(String dateString) {
    if (dateString == null) {
      return null;
    }
    try {
      return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid date format: ".concat(dateString));
    }
  }
}

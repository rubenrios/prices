package com.rubenrbr.prices.infrastructure.rest.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rubenrbr.prices.application.port.in.PriceService;
import com.rubenrbr.prices.infrastructure.rest.ApiPrice;
import com.rubenrbr.prices.infrastructure.rest.dto.PriceResponse;
import com.rubenrbr.prices.infrastructure.rest.mapper.PriceResponseMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PriceController implements ApiPrice {

  private final PriceService priceService;
  private final PriceResponseMapper mapper;

  @Override
  public ResponseEntity<PriceResponse> getApplicablePrice(
      String applicationDate, Long productId, Long brandId) {
    LocalDateTime localDateTime = mapper.toDateTime(applicationDate);
    return priceService
        .getApplicablePrice(localDateTime, productId, brandId)
        .map(mapper::toResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}

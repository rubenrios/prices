package com.rubenrbr.prices.infrastructure.rest.controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rubenrbr.prices.application.port.in.PriceService;
import com.rubenrbr.prices.domain.model.Price;
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
      OffsetDateTime applicationDate, Long productId, Long brandId) {
    LocalDateTime localDateTime = applicationDate.toLocalDateTime();
    Price price = priceService.getApplicablePrice(localDateTime, productId, brandId);
    return ResponseEntity.ok(mapper.toResponse(price));
  }
}

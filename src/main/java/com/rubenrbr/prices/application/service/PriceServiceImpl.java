package com.rubenrbr.prices.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.rubenrbr.prices.application.port.in.PriceService;
import com.rubenrbr.prices.domain.exception.PriceNotFoundException;
import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.domain.port.PriceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

  private final PriceRepository priceRepository;

  @Override
  public Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
    return priceRepository
        .findApplicablePrice(applicationDate, productId, brandId)
        .orElseThrow(() -> new PriceNotFoundException(applicationDate, productId, brandId));
  }
}

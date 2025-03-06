package com.rubenrbr.prices.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.rubenrbr.prices.application.port.in.PriceService;
import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.domain.port.PriceRepository;

public class PriceServiceImpl implements PriceService {

  private final PriceRepository priceRepository;

  public PriceServiceImpl(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  @Override
  public Optional<Price> getApplicablePrice(
      LocalDateTime applicationDate, Long productId, Long brandId) {
    return priceRepository.findApplicablePrice(applicationDate, productId, brandId);
  }
}

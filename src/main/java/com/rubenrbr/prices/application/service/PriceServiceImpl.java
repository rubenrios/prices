package com.rubenrbr.prices.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rubenrbr.prices.application.port.in.PriceService;
import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.domain.port.PriceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

  private final PriceRepository priceRepository;

  @Override
  public Optional<Price> getApplicablePrice(
      LocalDateTime applicationDate, Long productId, Long brandId) {
    return priceRepository.findApplicablePrice(applicationDate, productId, brandId);
  }
}

package com.rubenrbr.prices.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.domain.port.PriceRepository;
import com.rubenrbr.prices.infrastructure.persistence.mapper.PriceMapper;
import com.rubenrbr.prices.infrastructure.persistence.repository.PriceJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

  private final PriceJpaRepository priceJpaRepository;
  private final PriceMapper priceMapper;

  @Override
  public Optional<Price> findApplicablePrice(
      LocalDateTime applicationDate, Long productId, Long brandId) {
    return priceJpaRepository.findApplicablePrices(applicationDate, productId, brandId).stream()
        .findFirst()
        .map(priceMapper::toDomain);
  }
}

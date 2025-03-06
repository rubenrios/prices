package com.rubenrbr.prices.domain.port;

import java.time.LocalDateTime;
import java.util.Optional;

import com.rubenrbr.prices.domain.model.Price;

public interface PriceRepository {
  /**
   * Find the applicable price for a product, brand, and date
   *
   * @param applicationDate The date when the price is queried
   * @param productId The product identifier
   * @param brandId The brand identifier
   * @return The applicable price, if found
   */
  Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}

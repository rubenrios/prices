package com.rubenrbr.prices.application.port.in;

import java.time.LocalDateTime;
import java.util.Optional;

import com.rubenrbr.prices.domain.model.Price;

public interface PriceService {
  /**
   * Get the applicable price for a product at a specific date
   *
   * @param applicationDate The date to check for pricing
   * @param productId The product identifier
   * @param brandId The brand identifier
   * @return The applicable price, if found
   */
  Optional<Price> getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}

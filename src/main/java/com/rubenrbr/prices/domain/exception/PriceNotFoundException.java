package com.rubenrbr.prices.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

  public PriceNotFoundException(String message) {
    super(message);
  }

  public PriceNotFoundException(LocalDateTime applicationDate, Long productId, Long brandId) {
    super(
        String.format(
            "No applicable price found for product %d, brand %d at %s",
            productId, brandId, applicationDate));
  }
}

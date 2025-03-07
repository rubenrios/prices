package com.rubenrbr.prices.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rubenrbr.prices.application.port.in.PriceService;
import com.rubenrbr.prices.application.service.PriceServiceImpl;
import com.rubenrbr.prices.domain.port.PriceRepository;

@Configuration
public class ApplicationConfig {

  @Bean
  public PriceService priceService(PriceRepository priceRepository) {
    return new PriceServiceImpl(priceRepository);
  }
}

package com.rubenrbr.prices.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.infrastructure.persistence.entity.PriceEntity;
import com.rubenrbr.prices.infrastructure.persistence.mapper.PriceMapper;
import com.rubenrbr.prices.infrastructure.persistence.repository.PriceJpaRepository;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

  @Mock private PriceJpaRepository priceJpaRepository;

  @Mock private PriceMapper priceMapper;

  @InjectMocks private PriceRepositoryAdapter priceRepositoryAdapter;

  private LocalDateTime testDate;
  private Long productId;
  private Long brandId;
  private PriceEntity samplePriceEntity;
  private Price samplePrice;

  @BeforeEach
  void setUp() {
    testDate = LocalDateTime.parse("2020-06-14T10:00:00");
    productId = 35455L;
    brandId = 1L;

    samplePriceEntity = new PriceEntity();
    samplePriceEntity.setId(1L);
    samplePriceEntity.setBrandId(brandId);
    samplePriceEntity.setProductId(productId);
    samplePriceEntity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
    samplePriceEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
    samplePriceEntity.setPriceList(1);
    samplePriceEntity.setPriority(0);
    samplePriceEntity.setPrice(new BigDecimal("35.50"));
    samplePriceEntity.setCurrency("EUR");

    samplePrice =
        new Price(
            productId,
            brandId,
            1,
            LocalDateTime.parse("2020-06-14T00:00:00"),
            LocalDateTime.parse("2020-12-31T23:59:59"),
            0,
            new BigDecimal("35.50"),
            "EUR");
  }

  @Test
  @DisplayName("Should return price when found")
  void findApplicablePrice_PriceFound_ReturnsPrice() {
    when(priceJpaRepository.findApplicablePrices(testDate, productId, brandId))
        .thenReturn(List.of(samplePriceEntity));
    when(priceMapper.toDomain(samplePriceEntity)).thenReturn(samplePrice);

    Optional<Price> result =
        priceRepositoryAdapter.findApplicablePrice(testDate, productId, brandId);

    assertTrue(result.isPresent());
    assertEquals(samplePrice, result.get());
    verify(priceJpaRepository).findApplicablePrices(testDate, productId, brandId);
    verify(priceMapper).toDomain(samplePriceEntity);
  }

  @Test
  @DisplayName("Should return empty Optional when no price found")
  void findApplicablePrice_NoPriceFound_ReturnsEmptyOptional() {
    when(priceJpaRepository.findApplicablePrices(testDate, productId, brandId))
        .thenReturn(Collections.emptyList());

    Optional<Price> result =
        priceRepositoryAdapter.findApplicablePrice(testDate, productId, brandId);

    assertFalse(result.isPresent());
    verify(priceJpaRepository).findApplicablePrices(testDate, productId, brandId);
    verify(priceMapper, never()).toDomain(any());
  }

  @Test
  @DisplayName("Should select first price when multiple are found")
  void findApplicablePrice_MultiplePricesFound_ReturnsFirstPrice() {
    PriceEntity secondPriceEntity = new PriceEntity();
    secondPriceEntity.setId(2L);
    secondPriceEntity.setBrandId(brandId);
    secondPriceEntity.setProductId(productId);
    secondPriceEntity.setPriceList(2);
    secondPriceEntity.setPriority(1);

    when(priceJpaRepository.findApplicablePrices(testDate, productId, brandId))
        .thenReturn(List.of(samplePriceEntity, secondPriceEntity));
    when(priceMapper.toDomain(samplePriceEntity)).thenReturn(samplePrice);

    Optional<Price> result =
        priceRepositoryAdapter.findApplicablePrice(testDate, productId, brandId);

    assertTrue(result.isPresent());
    assertEquals(samplePrice, result.get());
    verify(priceJpaRepository).findApplicablePrices(testDate, productId, brandId);
    verify(priceMapper).toDomain(samplePriceEntity);
    verify(priceMapper, never()).toDomain(secondPriceEntity);
  }

  @Test
  @DisplayName("Should handle null parameters")
  void findApplicablePrice_NullParameters_PassedToRepository() {
    LocalDateTime nullDate = null;
    Long nullProductId = null;
    Long nullBrandId = null;

    when(priceJpaRepository.findApplicablePrices(nullDate, nullProductId, nullBrandId))
        .thenReturn(Collections.emptyList());

    Optional<Price> result =
        priceRepositoryAdapter.findApplicablePrice(nullDate, nullProductId, nullBrandId);

    assertFalse(result.isPresent());
    verify(priceJpaRepository).findApplicablePrices(nullDate, nullProductId, nullBrandId);
  }
}

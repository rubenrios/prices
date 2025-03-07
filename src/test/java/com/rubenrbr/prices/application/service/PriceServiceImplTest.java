package com.rubenrbr.prices.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rubenrbr.prices.domain.exception.PriceNotFoundException;
import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.domain.port.PriceRepository;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

  @Mock private PriceRepository priceRepository;

  @InjectMocks private PriceServiceImpl priceService;

  private LocalDateTime testDate;
  private Long productId;
  private Long brandId;
  private Price samplePrice;

  @BeforeEach
  void setUp() {
    testDate = LocalDateTime.parse("2020-06-14T10:00:00");
    productId = 35455L;
    brandId = 1L;

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
  void getApplicablePrice_PriceFound_ReturnsPrice() {

    when(priceRepository.findApplicablePrice(testDate, productId, brandId))
        .thenReturn(Optional.of(samplePrice));

    Price result = priceService.getApplicablePrice(testDate, productId, brandId);

    assertNotNull(result);
    assertEquals(productId, result.productId());
    assertEquals(brandId, result.brandId());
    assertEquals(new BigDecimal("35.50"), result.price());
    assertEquals("EUR", result.currency());
    verify(priceRepository).findApplicablePrice(testDate, productId, brandId);
  }

  @Test
  @DisplayName("Should throw PriceNotFoundException when price not found")
  void getApplicablePrice_PriceNotFound_ThrowsException() {

    when(priceRepository.findApplicablePrice(testDate, productId, brandId))
        .thenReturn(Optional.empty());

    PriceNotFoundException exception =
        assertThrows(
            PriceNotFoundException.class,
            () -> priceService.getApplicablePrice(testDate, productId, brandId));

    assertTrue(exception.getMessage().contains(String.valueOf(productId)));
    assertTrue(exception.getMessage().contains(String.valueOf(brandId)));
    verify(priceRepository).findApplicablePrice(testDate, productId, brandId);
  }
}

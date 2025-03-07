package com.rubenrbr.prices.infrastructure.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rubenrbr.prices.application.port.in.PriceService;
import com.rubenrbr.prices.domain.exception.PriceNotFoundException;
import com.rubenrbr.prices.domain.model.Price;
import com.rubenrbr.prices.infrastructure.rest.dto.PriceResponse;
import com.rubenrbr.prices.infrastructure.rest.mapper.PriceResponseMapper;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  @Mock private PriceService priceService;

  @Mock private PriceResponseMapper mapper;

  @InjectMocks private PriceController priceController;

  private OffsetDateTime testOffsetDateTime;
  private LocalDateTime testLocalDateTime;
  private Long productId;
  private Long brandId;
  private Price samplePrice;
  private PriceResponse samplePriceResponse;

  @BeforeEach
  void setUp() {
    testOffsetDateTime = OffsetDateTime.parse("2020-06-14T10:00:00Z");
    testLocalDateTime = testOffsetDateTime.toLocalDateTime();
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

    samplePriceResponse = new PriceResponse();
    samplePriceResponse.setProductId(productId);
    samplePriceResponse.setBrandId(brandId);
    samplePriceResponse.setPriceList(1);
    samplePriceResponse.setStartDate(OffsetDateTime.of(2020, 6, 14, 0, 0, 0, 0, ZoneOffset.UTC));
    samplePriceResponse.setEndDate(OffsetDateTime.of(2020, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC));
    samplePriceResponse.setPrice(35.50);
    samplePriceResponse.setCurrency("EUR");
  }

  @Test
  @DisplayName("Should return price response when price is found")
  void getApplicablePrice_PriceFound_ReturnsPriceResponse() {
    when(priceService.getApplicablePrice(testLocalDateTime, productId, brandId))
        .thenReturn(samplePrice);
    when(mapper.toResponse(samplePrice)).thenReturn(samplePriceResponse);

    ResponseEntity<PriceResponse> response =
        priceController.getApplicablePrice(testOffsetDateTime, productId, brandId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(samplePriceResponse, response.getBody());

    verify(priceService).getApplicablePrice(testLocalDateTime, productId, brandId);
    verify(mapper).toResponse(samplePrice);
  }

  @Test
  @DisplayName("Should convert OffsetDateTime to LocalDateTime correctly")
  void getApplicablePrice_ConvertsDateTimeCorrectly() {
    OffsetDateTime offsetDateTime = OffsetDateTime.of(2020, 6, 15, 16, 0, 0, 0, ZoneOffset.UTC);
    LocalDateTime expectedLocalDateTime = offsetDateTime.toLocalDateTime();

    when(priceService.getApplicablePrice(expectedLocalDateTime, productId, brandId))
        .thenReturn(samplePrice);
    when(mapper.toResponse(samplePrice)).thenReturn(samplePriceResponse);

    priceController.getApplicablePrice(offsetDateTime, productId, brandId);

    verify(priceService).getApplicablePrice(expectedLocalDateTime, productId, brandId);
  }

  @Test
  @DisplayName("Should propagate exceptions from service")
  void getApplicablePrice_ServiceThrowsException_PropagatesException() {
    when(priceService.getApplicablePrice(testLocalDateTime, productId, brandId))
        .thenThrow(new PriceNotFoundException(testLocalDateTime, productId, brandId));

    assertThrows(
        PriceNotFoundException.class,
        () -> priceController.getApplicablePrice(testOffsetDateTime, productId, brandId));

    verify(priceService).getApplicablePrice(testLocalDateTime, productId, brandId);
    verify(mapper, never()).toResponse(any());
  }

  @Test
  @DisplayName("Should handle null application date")
  void getApplicablePrice_NullApplicationDate_ThrowsNullPointerException() {
    OffsetDateTime nullDateTime = null;

    assertThrows(
        NullPointerException.class,
        () -> priceController.getApplicablePrice(nullDateTime, productId, brandId));

    verify(priceService, never()).getApplicablePrice(any(), any(), any());
  }
}

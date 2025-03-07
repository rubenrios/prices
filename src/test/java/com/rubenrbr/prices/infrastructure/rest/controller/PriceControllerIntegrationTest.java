package com.rubenrbr.prices.infrastructure.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  private static final Long PRODUCT_ID = 35455L;
  private static final Long BRAND_ID = 1L;
  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  @Test
  @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
  void getPrice_Test1() throws Exception {
    String dateTime = "2020-06-14T10:00:00Z";

    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", dateTime)
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Test 2: Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)")
  void getPrice_Test2() throws Exception {
    String dateTime = "2020-06-14T16:00:00Z";

    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", dateTime)
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.price").value(25.45));
  }

  @Test
  @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
  void getPrice_Test3() throws Exception {
    String dateTime = "2020-06-14T21:00:00Z";

    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", dateTime)
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(1))
        .andExpect(jsonPath("$.price").value(35.50));
  }

  @Test
  @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
  void getPrice_Test4() throws Exception {
    String dateTime = "2020-06-15T10:00:00Z";

    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", dateTime)
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(3))
        .andExpect(jsonPath("$.price").value(30.50));
  }

  @Test
  @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
  void getPrice_Test5() throws Exception {
    String dateTime = "2020-06-16T21:00:00Z";

    mockMvc
        .perform(
            get("/api/prices")
                .param("applicationDate", dateTime)
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
        .andExpect(jsonPath("$.brandId").value(BRAND_ID))
        .andExpect(jsonPath("$.priceList").value(4))
        .andExpect(jsonPath("$.price").value(38.95));
  }
}

package com.rubenrbr.prices.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rubenrbr.prices.infrastructure.persistence.entity.PriceEntity;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

  @Query(
      "SELECT p FROM PriceEntity p "
          + "WHERE p.productId = :productId "
          + "AND p.brandId = :brandId "
          + "AND :applicationDate BETWEEN p.startDate AND p.endDate "
          + "ORDER BY p.priority DESC")
  List<PriceEntity> findApplicablePrices(
      @Param("applicationDate") LocalDateTime applicationDate,
      @Param("productId") Long productId,
      @Param("brandId") Long brandId);
}

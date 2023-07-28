package bpp.repository;

import bpp.entity.CirclePriceEntity;
import bpp.entity.NestePriceEntity;
import bpp.util.Country;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CirclePriceRepository extends CrudRepository<CirclePriceEntity, UUID> {
    List<CirclePriceEntity> findTop5ByCreatedDateBeforeAndCountryOrderByCreatedDateAsc(LocalDateTime createdDate, Country country);

    @Query(value = "SELECT * FROM petrol_station.circle WHERE country= :country AND created_date <= :startDate AND created_date >= :endDate", nativeQuery = true)
    CirclePriceEntity searchLastDatePrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("country") String country);
}

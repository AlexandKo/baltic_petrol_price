package bpp.repository;

import bpp.entity.CirclePriceEntity;
import bpp.util.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CirclePriceRepository extends CrudRepository<CirclePriceEntity, UUID> {
    List<CirclePriceEntity> findTop5ByCreatedDateBeforeAndCountryOrderByCreatedDateAsc(LocalDateTime createdDate, Country country);

    @Query(value = "SELECT * FROM petrol_station.circle WHERE country= :country AND created_date <= :startDate AND created_date >= :endDate", nativeQuery = true)
    CirclePriceEntity searchLastDatePrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("country") String country);

    @Query(value = "SELECT * FROM petrol_station.circle WHERE country= :country AND created_date <= :startDate AND created_date >= :endDate ORDER BY created_date ASC", nativeQuery = true)
    List<CirclePriceEntity> searchPrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("country") String country);
}

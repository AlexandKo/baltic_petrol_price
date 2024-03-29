package bpp.repository;

import bpp.entity.GotikaPriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface GotikaPriceRepository extends CrudRepository<GotikaPriceEntity, UUID> {
    List<GotikaPriceEntity> findTop5ByCreatedDateBeforeOrderByCreatedDateAsc(LocalDateTime createdDate);

    @Query(value = "SELECT * FROM petrol_station.gotika WHERE created_date <= :startDate AND created_date >= :endDate", nativeQuery = true)
    GotikaPriceEntity searchLastDatePrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM petrol_station.gotika WHERE created_date <= :startDate AND created_date >= :endDate ORDER BY created_date ASC", nativeQuery = true)
    List<GotikaPriceEntity> searchPrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

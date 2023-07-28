package bpp.repository;

import bpp.entity.ViadaPriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ViadaPriceRepository extends CrudRepository<ViadaPriceEntity, UUID> {
    List<ViadaPriceEntity> findTop5ByCreatedDateBeforeOrderByCreatedDateAsc(LocalDateTime createdDate);

    @Query(value = "SELECT * FROM petrol_station.viada WHERE created_date <= :startDate AND created_date >= :endDate", nativeQuery = true)
    ViadaPriceEntity searchLastDatePrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

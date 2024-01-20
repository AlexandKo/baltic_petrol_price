package bpp.repository;

import bpp.entity.VirsiPriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VirsiPriceRepository extends CrudRepository<VirsiPriceEntity, UUID> {
    List<VirsiPriceEntity> findTop5ByCreatedDateBeforeOrderByCreatedDateAsc(LocalDateTime createdDate);

    @Query(value = "SELECT * FROM petrol_station.virsi WHERE created_date <= :startDate AND created_date >= :endDate", nativeQuery = true)
    VirsiPriceEntity searchLastDatePrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM petrol_station.virsi WHERE created_date <= :startDate AND created_date >= :endDate ORDER BY created_date ASC", nativeQuery = true)
    List<VirsiPriceEntity> searchPrices(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

package bpp.repository;

import bpp.entity.CirclePriceEntity;
import bpp.util.Country;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirclePriceRepository extends CrudRepository<CirclePriceEntity, UUID> {
    List<CirclePriceEntity> findTop5ByCreatedDateBeforeAndCountryOrderByCreatedDateDesc(LocalDateTime createdDate, Country country);
}

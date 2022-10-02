package bpp.repository;

import bpp.entity.NestePriceEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NestePriceRepository extends CrudRepository<NestePriceEntity, UUID> {
    List<NestePriceEntity> findTop5ByCreatedDateBeforeOrderByCreatedDateDesc(LocalDateTime createdDate);

}

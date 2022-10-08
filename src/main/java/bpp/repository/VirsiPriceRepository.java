package bpp.repository;

import bpp.entity.VirsiPriceEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirsiPriceRepository extends CrudRepository<VirsiPriceEntity, UUID> {
    List<VirsiPriceEntity> findTop5ByCreatedDateBeforeOrderByCreatedDateDesc(LocalDateTime createdDate);
}

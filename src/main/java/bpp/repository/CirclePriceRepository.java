package bpp.repository;

import bpp.entity.CirclePriceEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirclePriceRepository extends CrudRepository<CirclePriceEntity, UUID> {
}

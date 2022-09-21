package bpp.repository;

import bpp.entity.GotikaPriceEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GotikaPriceRepository extends CrudRepository<GotikaPriceEntity, UUID> {
}

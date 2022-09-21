package bpp.repository;

import bpp.entity.ViadaPriceEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViadaPriceRepository extends CrudRepository<ViadaPriceEntity, UUID> {
}

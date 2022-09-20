package bpp.repository;

import bpp.entity.NestePriceEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NestePriceRepository extends CrudRepository<NestePriceEntity, UUID> {
}

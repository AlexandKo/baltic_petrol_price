package bpp.repository;

import bpp.entity.VirsiPriceEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirsiPriceRepository extends CrudRepository<VirsiPriceEntity, UUID> {
}

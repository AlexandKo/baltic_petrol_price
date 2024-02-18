package bpp.repository;

import bpp.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditEntityRepository extends JpaRepository<AuditEntity, UUID> {
}
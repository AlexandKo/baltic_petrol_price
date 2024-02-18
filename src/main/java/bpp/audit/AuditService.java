package bpp.audit;

import bpp.entity.AuditEntity;
import bpp.model.AuditModel;
import bpp.repository.AuditEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditEntityRepository auditEntityRepository;

    public void save(AuditModel auditModel) {
        AuditEntity auditEntity = AuditEntity.builder()
                .ipAddress(auditModel.ipAddress())
                .requestUri(auditModel.requestUri())
                .build();
        auditEntityRepository.save(auditEntity);
    }
}

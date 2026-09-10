package com.npst.loggingapi.repository;

import com.npst.loggingapi.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditLogRepository
        extends JpaRepository<AuditLogEntity, String> {
    Optional<AuditLogEntity> findByEventHash(String eventHash);
    boolean existsByEventHash(String eventHash);

}
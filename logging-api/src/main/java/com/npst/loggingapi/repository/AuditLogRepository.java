package com.npst.loggingapi.repository;

import com.npst.loggingapi.entity.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuditLogRepository
        extends JpaRepository<AuditLogEntity, String> {
    Optional<AuditLogEntity> findByEventHash(String eventHash);
    boolean existsByEventHash(String eventHash);
    @Query("""
SELECT a FROM AuditLogEntity a
WHERE (:customerId IS NULL OR a.customerId = :customerId)
AND (:module IS NULL OR a.module = :module)
AND (:action IS NULL OR a.action = :action)
AND (:traceId IS NULL OR a.traceId = :traceId)
""")
    Page<AuditLogEntity> search(
            @Param("customerId") String customerId,
            @Param("module") String module,
            @Param("action") String action,
            @Param("traceId") String traceId,
            Pageable pageable
    );
}
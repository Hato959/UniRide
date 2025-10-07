package com.uniride.repository;

import com.uniride.model.Notificacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Query("""
      SELECT n FROM Notificacion n
      WHERE n.status = :status AND (n.scheduledAt IS NULL OR n.scheduledAt <= :now)
      ORDER BY n.createdAt ASC
  """)
    List<Notificacion> findDue(@Param("status") String status, @Param("now") OffsetDateTime now);

    List<Notificacion> findByUsuario_IdOrderByCreatedAtDesc(Long usuarioId);
}

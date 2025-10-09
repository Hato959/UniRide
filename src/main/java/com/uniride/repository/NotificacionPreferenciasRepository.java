package com.uniride.repository;

import com.uniride.model.NotificacionPreferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NotificacionPreferenciasRepository extends JpaRepository<NotificacionPreferencias, Long> {
    Optional<NotificacionPreferencias> findByUsuario_Id(Long usuarioId);
}
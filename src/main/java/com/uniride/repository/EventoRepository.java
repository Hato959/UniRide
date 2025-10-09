package com.uniride.repository;
import com.uniride.model.Evento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByViajeIdOrderByFechaHoraAsc(Long viajeId);
}

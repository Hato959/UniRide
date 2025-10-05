package com.uniride.repository;

import com.uniride.model.Conductor;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ConductorRepository extends JpaRepository<Conductor, Long>{
    @Query("""
        SELECT c FROM Conductor c
        JOIN FETCH c.usuario u
        LEFT JOIN FETCH c.vehiculos v
        WHERE c.id = :id
    """)
    Optional<Conductor> findByUsuarioId(@Param("id") Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);
    //Optional<Conductor> findByUsuarioId(Long usuarioId);
}

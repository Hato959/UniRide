package com.uniride.repository;

import com.uniride.model.Conductor;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ConductorRepository extends JpaRepository<Conductor, Long>{
    @Query("""
        SELECT c FROM Conductor c
        JOIN FETCH c.usuario u
        WHERE u.id = :usuarioId
    """)
    Optional<Conductor> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);
    //Optional<Conductor> findByUsuarioId(Long usuarioId);
}

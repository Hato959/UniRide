package com.uniride.repository;

import com.uniride.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long>{
    boolean existsByUsuarioId(Long usuarioId);


    Optional<Pasajero> findByUsuarioId(Long usuarioId);
}
package com.uniride.repository;

import com.uniride.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long>{
    boolean existsByUsuarioId(Long usuarioId);
}

package com.uniride.repository;

import com.uniride.model.CodigoVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long>{
    Optional<CodigoVerificacion> findByCorreoAndCodigoAndUsadoFalse(String correo, String codigo);
}

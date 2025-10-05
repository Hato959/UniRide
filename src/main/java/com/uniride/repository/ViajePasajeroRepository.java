package com.uniride.repository;

import com.uniride.model.ViajePasajero;
import com.uniride.model.ViajePasajeroId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViajePasajeroRepository extends JpaRepository<ViajePasajero, ViajePasajeroId> {
    long countByViajeId(Long viajeId);
    List<ViajePasajero> findByViajeId(Long viajeId);
    boolean existsByViajeIdAndPasajeroId(Long viajeId, Long pasajeroId);
}

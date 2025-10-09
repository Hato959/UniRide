package com.uniride.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uniride.model.ViajePasajero;
import com.uniride.model.ViajePasajeroId;


import java.util.List;

@Repository
public interface ViajePasajeroRepository extends JpaRepository<ViajePasajero,ViajePasajeroId> {
    long countByViajeId(Long viajeId);

    boolean existsByViajeIdAndPasajeroId(Long viajeId, Long pasajeroId);

    List<ViajePasajero> findByViajeId(Long viajeId);
}

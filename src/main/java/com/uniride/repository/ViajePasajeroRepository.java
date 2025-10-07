package com.uniride.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uniride.model.ViajePasajero;

import java.util.Optional;

@Repository
public interface ViajePasajeroRepository extends JpaRepository<ViajePasajero,Long> {
    Optional<ViajePasajero> findById(Long id);
}

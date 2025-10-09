package com.uniride.repository;

import com.uniride.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByViajeId(Long viajeId);
}

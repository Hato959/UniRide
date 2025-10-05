package com.uniride.repository;
import com.uniride.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{
    List<Vehiculo> findByConductorId(Long conductorId);
}

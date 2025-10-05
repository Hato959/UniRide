package com.uniride.repository;
import com.uniride.model.Vehiculo;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{
    boolean existsByPlaca(String placa);
    List<Vehiculo> findByConductorId(Long conductorId);
}

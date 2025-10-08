package com.uniride.repository;

import com.uniride.model.Pago;
import com.uniride.model.ViajePasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago,Long> {
    /*
    @Query("""
        SELECT p FROM Pago p
        JOIN FETCH p.viajePasajero vp
        WHERE p.id = :id
    """)
    Optional<Pago> findById(@Param("id") long id);

    @Query("""
        SELECT vp FROM ViajePasajero vp
        JOIN FETCH vp.id vpid
        WHERE vpid = :id_pasajero
    """)
    ViajePasajero findByViajePasajero_Id(@Param("id_pasajero") long viajePasajeroId);

    @Query("""
        SELECT p FROM Pago p
        JOIN FETCH p.idConductor idC
        WHERE p.idConductor = :id_conductor
    """)
    Optional<Pago> findByConductorId(@Param("id_conductor") long idConductor);
    */
}

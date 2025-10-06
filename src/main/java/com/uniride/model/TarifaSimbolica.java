package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tarifas_simbolicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TarifaSimbolica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Long id;

    @Column(name="monto_total", nullable = false)
    private Double montoTotal;

    @Column(name="num_pasajeros", nullable = false)
    private Long numPasajeros;

    @Column(name="id_conductor", nullable = false)
    private Long conductorId;

    @Column(name="id_vehiculo", nullable = false)
    private Long vehiculoId;

    @Column(name="precio_por_persona", nullable = false)
    private Double precioPorPersona;

    @Column(name="metodo_pago", nullable = false, length = 50)
    private String metodoPago; // por defecto, efectivo

    @Column(name="fecha_creacion", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(name="id_viaje", nullable = false)
    private Long viajeId;
}

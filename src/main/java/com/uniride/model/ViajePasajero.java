package com.uniride.model;
import com.uniride.model.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "viaje_pasajero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViajePasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje_pasajero")
    private Long id_viajePasajero;

    @Column(name = "id_pasajero", nullable = false)
    private Long idPasajero;

    @Column(name = "id_viaje", nullable = false)
    private Long idViaje;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(name = "estado_reserva", nullable = false, length = 20)
    private String estadoReserva;
}

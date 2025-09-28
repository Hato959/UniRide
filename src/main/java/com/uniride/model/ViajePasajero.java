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
    @EmbeddedId
    private ViajePasajeroId id;

    @ManyToOne
    @MapsId("viajeId")
    @JoinColumn(name = "id_viaje", nullable = false)
    private Viaje viaje;

    @ManyToOne
    @MapsId("pasajeroId")
    @JoinColumn(name = "id_pasajero", nullable = false)
    private Pasajero pasajero;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reserva", nullable = false, length = 20)
    private EstadoReserva estadoReserva;

    @OneToOne(mappedBy = "viajePasajero", cascade = CascadeType.ALL)
    private Pago pago;
}

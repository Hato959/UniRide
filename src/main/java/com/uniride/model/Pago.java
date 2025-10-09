package com.uniride.model;


import com.uniride.model.enums.EstadoPago;
import com.uniride.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.math.BigDecimal;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "id_conductor", nullable = false)
    private Long idConductor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MetodoPago metodo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPago estado;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;


    // Relación con ViajePasajero
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "id_viaje", referencedColumnName = "id_viaje"),
            @JoinColumn(name = "id_pasajero", referencedColumnName = "id_pasajero")
    })
    private ViajePasajero viajePasajero;
    //@Column(name = "id_viaje_pasajero", nullable = false)
    //private Long viajePasajeroId;
}

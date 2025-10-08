package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "viajes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conductor", nullable = false)
    private Conductor conductor;

    @Column(nullable = false, length = 100)
    private String origen;

    @Column(nullable = false, length = 100)
    private String destino;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "asientos_disponibles", nullable = false)
    private Integer asientosDisponibles;

    @Column(nullable = false)
    private Boolean recurrente = false;

    // Relación con pasajeros (muchos a muchos usando ViajePasajero)
    //@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    //private List<ViajePasajero> pasajeros;
}

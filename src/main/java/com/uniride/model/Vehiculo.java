package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conductor", nullable = false)
    private Conductor conductor;

    @Column(nullable = false, length = 20)
    private String marca;

    @Column(nullable = false, length = 20, unique = true)
    private String placa;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false, length = 30)
    private String color;
    @Column(name = "foto_vehiculo_url", length = 255)
    private String fotoVehiculoUrl;
}

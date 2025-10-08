package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "conductores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conductor")
    private Long id;

    // FK a Usuario, único (1:1)
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "licencia_conducir", nullable = false, length = 50)
    private String licenciaConducir;

    @Column(name = "experiencia_anios")
    private Integer experienciaAnios;

    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos = new ArrayList<>();
}

package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pasajeros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pasajero")
    private Long id;

    // FK a Usuario, único (1:1)
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(columnDefinition = "text")
    private String preferencias;
}

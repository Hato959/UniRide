package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resenas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_viaje", nullable = false)
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "id_destinatario", nullable = false)
    private Usuario destinatario;

    @Column(nullable = false)
    private Integer calificacion; // 1–5

    @Column(columnDefinition = "TEXT")
    private String comentario;
}

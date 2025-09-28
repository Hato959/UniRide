package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo_institucional",nullable = false,length = 100,unique = true)
    private String correoInsitucional;

    @Column(nullable = false, length = 100)
    private String contrasena;

    @Column(length = 100)
    private String carrera;

    @Column(length = 100)
    private String distrito;

    @Column(nullable = false, length = 8, unique = true)
    private String dni;

    @Column(name = "verificado")
    private Boolean verificado = false;

}

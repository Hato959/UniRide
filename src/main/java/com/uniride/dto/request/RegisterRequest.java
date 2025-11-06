package com.uniride.dto.request;

public record RegisterRequest(
        String nombre,
        String correoInstitucional,
        String contrasena,
        String carrera,
        String distrito,
        String dni
) {
}

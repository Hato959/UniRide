package com.uniride.dto.request;

import com.uniride.model.enums.RolActivo;

public record RegisterRequest(
        String nombre,
        String correoInstitucional,
        String contrasena,
        String carrera,
        String distrito,
        String dni,
        RolActivo rolActivo
) {
}

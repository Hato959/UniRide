package com.uniride.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CambiarPasswordRequestDTO(
        @NotBlank(message = "La contraseña actual es obligatoria")
        String contrasenaActual,

        @NotBlank(message = "La nueva contraseña es obligatoria")
        String nuevaContrasena
) {}

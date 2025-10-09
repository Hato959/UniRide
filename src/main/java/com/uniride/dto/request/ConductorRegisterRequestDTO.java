package com.uniride.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record ConductorRegisterRequestDTO(
        @NotNull(message = "El id del usuario es obligatorio")
        Long usuarioId,

        @NotBlank(message = "La licencia de conducir es obligatoria")
        String licenciaConducir,

        @NotNull(message = "Los años de experiencia son obligatorios")
        Integer experienciaAnios
) {
}

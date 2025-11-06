package com.uniride.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record ConductorRegisterRequestDTO(
        @NotNull(message = "El id del usuario es obligatorio")
        Long usuarioId,

        @NotNull(message = "La licencia de conducir es un campo obligatorio")
        @NotBlank(message = "El campo de licencia de conducir no puede estar vacío")
        String licenciaConducir,

        @NotNull(message = "El campo de años de experiencia es obligatorio")
        Integer experienciaAnios
) {
}

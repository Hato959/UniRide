package com.uniride.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record ConductorRegisterRequestDTO(
        @NotNull Long usuarioId,
        @NotBlank String licenciaConducir,
        @NotNull Integer experienciaAnios
) {
}

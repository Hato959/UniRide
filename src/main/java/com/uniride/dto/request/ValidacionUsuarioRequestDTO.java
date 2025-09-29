package com.uniride.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record ValidacionUsuarioRequestDTO(
        @NotNull Long usuarioId,
        @NotBlank String dni,
        @NotBlank String codigoVerificacion
) {
}

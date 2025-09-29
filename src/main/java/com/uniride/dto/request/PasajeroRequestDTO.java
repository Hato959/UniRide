package com.uniride.dto.request;

import jakarta.validation.constraints.NotNull;

public record PasajeroRequestDTO(
        @NotNull Long usuarioId,
        String preferencias
) {
}

package com.uniride.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReservaRequestDTO(
        @NotNull Long idViaje,
        @NotNull Long idPasajero,
        @NotNull String metodoPago // "efectivo" | "yape" | "plin"
) {}

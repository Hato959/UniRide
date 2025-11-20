package com.uniride.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        Long usuarioId,
        Long conductorId,
        Long pasajeroId,
        String nombre,
        String rol
) {
}
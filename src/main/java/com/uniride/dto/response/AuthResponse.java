package com.uniride.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        String nombre,
        String rol
) {
}

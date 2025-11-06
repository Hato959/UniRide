package com.uniride.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
public record LoginRequest(
        String correo,
        String contrasena) {
}

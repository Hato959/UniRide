package com.uniride.dto.response;

public record PasajeroResponseDTO(
        Long id,
        Long usuarioId,
        String nombreUsuario,
        String correo,
        String preferencias
) {
}

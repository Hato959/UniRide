package com.uniride.dto.response;
import lombok.Builder;

@Builder
public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String correoInstitucional,
        String carrera,
        String distrito,
        String dni,
        String rol,
        Boolean verificado) {
}

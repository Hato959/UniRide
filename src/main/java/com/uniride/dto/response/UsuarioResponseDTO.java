package com.uniride.dto.response;
import lombok.Builder;

@Builder
public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String correoInsitucional,
        String carrera,
        String distrito,
        String dni,
        Boolean verificado) {
}

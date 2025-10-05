package com.uniride.dto.response;

import lombok.Builder;

@Builder
public record ConductorInfoResponseDTO(
        Long id,
        Long usuarioId,
        String usuarioNombre,
        String usuarioCorreoInstitucional,
        String carrera,
        String distrito,
        String dni,
        String licenciaConducir,
        Integer experienciaAnios
) {
}

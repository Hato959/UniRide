package com.uniride.dto.response;

public record ConductorResponseDTO(
        Long id,
        Long usuarioId,
        String licenciaConducir,
        Integer experienciaAnios
) {
}

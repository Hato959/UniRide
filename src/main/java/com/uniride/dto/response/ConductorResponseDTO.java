package com.uniride.dto.response;

import lombok.Builder;

@Builder
public record ConductorResponseDTO(
        Long id,
        Long usuarioId,
        String licenciaConducir,
        Integer experienciaAnios
) {
}


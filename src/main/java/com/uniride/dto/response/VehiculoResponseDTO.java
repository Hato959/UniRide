package com.uniride.dto.response;

import lombok.Builder;

@Builder
public record VehiculoResponseDTO(
        Long id,
        Long conductorId,
        String marca,
        String placa,
        String modelo,
        String color,
        String fotoVehiculoUrl
) {
}

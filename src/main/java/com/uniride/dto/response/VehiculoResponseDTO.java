package com.uniride.dto.response;

public record VehiculoResponseDTO(
        Long id,
        Long conductorId,
        String marca,
        String placa,
        String modelo,
        String color
) {
}

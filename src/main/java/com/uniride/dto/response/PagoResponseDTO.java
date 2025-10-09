package com.uniride.dto.response;

import com.uniride.model.ViajePasajero;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PagoResponseDTO(
        Long id,
        Long idConductor,
        String monto,
        String metodo,
        String estado,
        Long viajePasajeroId,
        LocalDateTime fecha
) {
}
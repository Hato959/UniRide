package com.uniride.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record TarifaSimbolicaResponseDTO(
        Long id,
        Double montoTotal,
        Long numPasajeros,
        Long conductorId,
        Long vehiculoId,
        Double precioPorPersona,
        String metodoPago,
        LocalDateTime fechaCreacion,
        Long viajeId
) {
}

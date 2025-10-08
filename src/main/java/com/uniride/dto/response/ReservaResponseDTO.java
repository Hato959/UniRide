package com.uniride.dto.response;

import lombok.Builder;

@Builder
public record ReservaResponseDTO(
        Long idViaje,
        Long idPasajero,
        String estado // CONFIRMADA | CANCELADA | etc (según tu enum)
) {}

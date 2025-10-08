package com.uniride.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservaListadoDTO(
        Long idViaje,
        Long idPasajero,
        String nombrePasajero,
        String correoPasajero,
        String estadoReserva,        
        LocalDateTime fechaReserva   
) {}

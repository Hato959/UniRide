package com.uniride.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ViajeResponseDTO(
        Long idViaje,
        Long idConductor,
        String nombreConductor,
        String origen,
        String destino,
        LocalDate fecha,
        LocalTime hora,
        Integer asientosDisponibles,
        Boolean recurrente
) {}

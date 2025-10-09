package com.uniride.dto.response;

import com.uniride.model.enums.DiaSemana;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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
        Boolean recurrente,
        LocalDate fechaFinRecurrencia,
        Set<DiaSemana> diasRecurrencia
) {}

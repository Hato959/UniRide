package com.uniride.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.util.Set;

import java.time.LocalDate;
import java.time.LocalTime;

import com.uniride.model.enums.DiaSemana;

@Builder
public record ViajeRequestDTO(
        Long idConductor,
        String origen,
        String destino,
        LocalDate fecha,             
        LocalTime hora,
        Integer asientosDisponibles,
        Boolean recurrente,
        LocalDate fechaFinRecurrencia,
        Set<DiaSemana> diasRecurrencia
) {}

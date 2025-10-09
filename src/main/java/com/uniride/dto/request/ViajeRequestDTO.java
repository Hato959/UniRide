package com.uniride.dto.request;

import com.uniride.model.enums.DiaSemana;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Builder
public record ViajeRequestDTO(
        @NotNull(message = "El id del conductor es obligatorio")
        Long idConductor,

        @NotBlank(message = "El origen es obligatorio")
        String origen,

        @NotBlank(message = "El destino es obligatorio")
        String destino,

        @NotNull(message = "La fecha es obligatoria")
        @Future(message = "La fecha debe ser futura")
        LocalDate fecha,

        @NotNull(message = "La hora es obligatoria")
        LocalTime hora,

        @Min(value = 1, message = "Debe haber al menos 1 asiento disponible")
        Integer asientosDisponibles,

        Boolean recurrente,
        LocalDate fechaFinRecurrencia,
        Set<DiaSemana> diasRecurrencia
) {}

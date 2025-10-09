package com.uniride.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ViajeRecurrenteRequestDTO(
        @NotNull(message = "El id del conductor es obligatorio")
        Long idConductor,

        @NotBlank(message = "El origen es obligatorio")
        String origen,
        @NotBlank(message = "El destino es obligatorio")
        String destino,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @FutureOrPresent(message = "La fecha de inicio debe ser hoy o futura")
        LocalDate fechaInicio,

        @NotNull(message = "La hora es obligatoria")
        LocalTime hora,

        @Min(value = 1, message = "Debe haber al menos 1 asiento disponible")
        Integer asientosDisponibles,

        /*
         * Frecuencia del viaje: "DIARIA" o "SEMANAL"
         */
        @NotBlank(message = "La frecuencia es obligatoria (DIARIA o SEMANAL)")
        String frecuencia,

        /*
         * Cantidad de repeticiones solicitadas (incluye la primera).
         * Si es null, usaremos un valor por defecto (p.ej. 10) pero siempre limitado a 30 días.
         */
        Integer repeticiones) {
}

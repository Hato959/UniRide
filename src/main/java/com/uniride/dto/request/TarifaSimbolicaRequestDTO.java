package com.uniride.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record TarifaSimbolicaRequestDTO(
        @NotBlank(message = "El ID del conductor es obligatorio")
        Long conductorId,

        @NotBlank(message = "El ID del vehículo es obligatorio")
        Long vehiculoId,

        @NotBlank(message = "El monto total es obligatorio")
        @Positive(message = "El monto total debe ser un valor positivo")
        Double montoTotal,

        @NotBlank(message = "El número de pasajeros es obligatorio")
        @Min(value = 1, message = "Debe haber al menos un pasajero")
        Long numPasajeros,

        String metodoPago,

        @NotBlank(message = "El ID del viaje es obligatorio")
        Long viajeId
) {
}

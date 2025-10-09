package com.uniride.dto.request;

import com.uniride.model.ViajePasajero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PagoRequestDTO(
    @NotNull(message = "El id del conductor es obligatorio")
    Long idConductor,

    @NotNull(message = "El id del viaje es obligatorio")
    Long idViaje,

    @NotNull(message = "El id del pasajero es obligatorio")
    Long idPasajero,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto total debe ser un valor positivo")
    BigDecimal monto,


    String metodo,// puede ser nulo, por defecto se pondrá EFECTIVO en el service

    String estado // puede ser nulo, por defecto se mantendrá o se pondrá PENDIENTE
) {
}

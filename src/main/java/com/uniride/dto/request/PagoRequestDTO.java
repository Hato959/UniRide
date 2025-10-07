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

    @NotNull(message = "El id del viaje pasajero es obligatorio")
    //ViajePasajero viajePasajero,
    Long viajePasajeroId,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto total debe ser un valor positivo")
    BigDecimal monto,

    @NotBlank(message = "El método de pago es obligatorio")
    String metodo,

    String estado
) {
}

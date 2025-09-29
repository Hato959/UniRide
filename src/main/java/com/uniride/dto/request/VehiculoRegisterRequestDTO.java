package com.uniride.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record VehiculoRegisterRequestDTO(
        @NotNull Long conductorId,
        @NotBlank String marca,
        @NotBlank String placa,
        @NotBlank String modelo,
        @NotBlank String color
) {
}

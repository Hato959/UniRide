package com.uniride.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionPreferenciasRequestDTO {

    @NotNull(message = "El campo inAppEnabled no puede ser nulo")
    private Boolean inAppEnabled;

    @NotNull(message = "El campo emailEnabled no puede ser nulo")
    private Boolean emailEnabled;

    @Min(value = 0, message = "El recordatorio debe ser un número positivo o cero")
    private Integer reminderMinutesBefore;
}

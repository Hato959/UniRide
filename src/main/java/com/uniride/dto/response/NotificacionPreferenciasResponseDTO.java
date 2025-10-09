package com.uniride.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionPreferenciasResponseDTO {
    private Boolean inAppEnabled;
    private Boolean emailEnabled;
    private Integer reminderMinutesBefore;
}

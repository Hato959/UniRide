package com.uniride.controller;

import com.uniride.dto.request.NotificacionPreferenciasRequestDTO;
import com.uniride.dto.response.NotificacionPreferenciasResponseDTO;
import com.uniride.model.NotificacionPreferencias;
import com.uniride.service.NotificacionPreferenciasService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones/preferencias")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")

public class NotificacionPreferenciasController {

    private final NotificacionPreferenciasService service;

    @GetMapping("/{usuarioId}")
    public NotificacionPreferenciasResponseDTO getPreferencias(@PathVariable Long usuarioId) {
        NotificacionPreferencias prefs = service.getByUsuarioId(usuarioId);
        return NotificacionPreferenciasResponseDTO.builder()
                .inAppEnabled(prefs.getInAppEnabled())
                .emailEnabled(prefs.getEmailEnabled())
                .reminderMinutesBefore(prefs.getReminderMinutesBefore())
                .build();
    }

    @PutMapping("/{usuarioId}")
    public NotificacionPreferenciasResponseDTO updatePreferencias(
            @PathVariable Long usuarioId,
            @RequestBody NotificacionPreferenciasRequestDTO request) {

        NotificacionPreferencias updated = service.updatePreferencias(
                usuarioId,
                NotificacionPreferencias.builder()
                        .inAppEnabled(request.getInAppEnabled())
                        .emailEnabled(request.getEmailEnabled())
                        .reminderMinutesBefore(request.getReminderMinutesBefore())
                        .build()
        );

        return NotificacionPreferenciasResponseDTO.builder()
                .inAppEnabled(updated.getInAppEnabled())
                .emailEnabled(updated.getEmailEnabled())
                .reminderMinutesBefore(updated.getReminderMinutesBefore())
                .build();
    }
}

package com.uniride.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniride.model.enums.TipoNotificacion;
import com.uniride.model.Notificacion;
import com.uniride.service.NotificacionOrchestrator;
import com.uniride.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")

public class NotificacionController {

    private final NotificacionRepository repo;
    private final NotificacionOrchestrator orchestrator;

    // GET in-app (latest)
    @GetMapping
    public List<Notificacion> list(@RequestParam Long usuarioId) {
        return repo.findByUsuario_IdOrderByCreatedAtDesc(usuarioId);
    }

    // TEST: create a dummy notification now (for any canal)
    @PostMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> test(@RequestBody Map<String, Object> body) {
        Long usuarioId = ((Number) body.get("userId")).longValue();
        String tipo = (String) body.getOrDefault("tipo", TipoNotificacion.T_RESERVA_CONFIRMADA);
        String titulo = (String) body.getOrDefault("titulo", "Reserva confirmada");
        String cuerpo = (String) body.getOrDefault("cuerpo", "Tu reserva ha sido confirmada");
        String canal = (String) body.getOrDefault("canal", null); // null → use preferences
        //String payload = (String) body.getOrDefault("payloadJson", null);
        Map<String, Object> payload = null;
        String raw = (String) body.get("payloadJson"); // user may pass a raw JSON string
        if (raw != null && !raw.isBlank()) {
            try {
                payload = new ObjectMapper().readValue(raw, new TypeReference<>() {
                });
            } catch (Exception ex) {
                throw new RuntimeException("Error al parsear payloadJson: " + ex.getMessage(), ex);
            }
        }
        var ids = orchestrator.createForUser(usuarioId, tipo, titulo, cuerpo, canal, OffsetDateTime.now(), payload);
        return Map.of("createdIds", ids);
    }
}

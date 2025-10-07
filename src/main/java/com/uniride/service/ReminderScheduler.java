package com.uniride.service;

import com.uniride.model.enums.TipoNotificacion;
import com.uniride.model.NotificacionPreferencias;
import com.uniride.model.Usuario;
import com.uniride.repository.NotificacionPreferenciasRepository;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

// This example assumes you have a way to query upcoming trips per user.
// Replace the TODO with your Viaje repository call.
@Service @RequiredArgsConstructor
public class ReminderScheduler {
    private final NotificacionOrchestrator orchestrator;
    private final NotificacionPreferenciasRepository prefRepo;
    private final UsuarioRepository usuarioRepo;

    @Scheduled(fixedDelay = 60000) // every 1 min
    public void scheduleTripReminders() {
        // TODO: query upcoming trips within next 24h; for demo we'll skip and show the API shape
        // Example pseudo:
        // List<Viaje> viajes = viajeRepo.findBetween(now, now.plusHours(24));
        // for each viaje and each participant (conductor/pasajeros):
        //   compute scheduledAt = viaje.getFechaHora().minusMinutes(prefs.reminderMinutesBefore)
        //   orchestrator.createForUser(userId, Notif.RECORDATORIO_VIAJE, ..., null, scheduledAt, payloadJson);

        // No-op placeholder to show structure
    }
}

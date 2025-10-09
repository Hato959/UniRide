package com.uniride.service;

import com.uniride.model.Notificacion;
import com.uniride.model.enums.TipoNotificacion;
import com.uniride.repository.NotificacionRepository;
import com.uniride.service.publish.EmailPublisher;
import com.uniride.service.publish.InAppPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class NotificacionDispatcher {
    private final NotificacionRepository repo;
    private final InAppPublisher inApp;
    private final EmailPublisher email;

    @Scheduled(fixedDelay = 15000) // every 15s
    public void dispatch() {
        List<Notificacion> due = repo.findDue(TipoNotificacion.S_PENDING, OffsetDateTime.now());
        for (Notificacion n : due) {
            try {
                n.setStatus(TipoNotificacion.S_SENDING);
                repo.save(n);

                switch (n.getCanal()) {
                    case TipoNotificacion.C_IN_APP -> inApp.send(n);
                    case TipoNotificacion.C_EMAIL  -> email.send(n);
                    default -> throw new IllegalArgumentException("Canal desconocido: " + n.getCanal());
                }

                n.setStatus(TipoNotificacion.S_SENT);
                n.setSentAt(OffsetDateTime.now());
                n.setErrorMsg(null);
            } catch (Exception e) {
                n.setStatus(TipoNotificacion.S_FAILED);
                n.setErrorMsg(e.getMessage());
            }
            repo.save(n);
        }
    }
}

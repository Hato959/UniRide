package com.uniride.service.publish;

import com.uniride.model.Notificacion;
import com.uniride.service.EmailService;
import com.uniride.service.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class EmailPublisher implements NotificationPublisher {
    private final EmailService emailService;
    @Override public void send(Notificacion n) throws Exception {
        // You likely have usuario.getCorreoInstitucional()
        String to = n.getUsuario().getCorreoInstitucional();
        emailService.enviarCorreo(to, n.getTitulo(), n.getCuerpo());
    }
}

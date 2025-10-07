package com.uniride.service.publish;

import com.uniride.model.Notificacion;
import com.uniride.service.NotificationPublisher;
import org.springframework.stereotype.Component;

@Component
public class InAppPublisher implements NotificationPublisher {
    @Override public void send(Notificacion n) { /* nothing to do */ }
}

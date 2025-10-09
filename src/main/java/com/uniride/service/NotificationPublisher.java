package com.uniride.service;

import com.uniride.model.Notificacion;

public interface NotificationPublisher {
    void send(Notificacion n) throws Exception;
}

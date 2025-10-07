package com.uniride.service;

import com.uniride.model.NotificacionPreferencias;
import com.uniride.repository.NotificacionPreferenciasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacionPreferenciasService {

    private final NotificacionPreferenciasRepository repository;

    public NotificacionPreferencias getByUsuarioId(Long usuarioId) {
        return repository.findByUsuario_Id(usuarioId)
                .orElseThrow(() -> new RuntimeException("Preferencias no encontradas"));
    }

    public NotificacionPreferencias updatePreferencias(Long usuarioId, NotificacionPreferencias nuevas) {
        NotificacionPreferencias existentes = getByUsuarioId(usuarioId);
        existentes.setInAppEnabled(nuevas.getInAppEnabled());
        existentes.setEmailEnabled(nuevas.getEmailEnabled());
        existentes.setReminderMinutesBefore(nuevas.getReminderMinutesBefore());
        return repository.save(existentes);
    }
}

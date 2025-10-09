package com.uniride.service;

import com.uniride.model.enums.TipoNotificacion;
import com.uniride.model.Notificacion;
import com.uniride.model.NotificacionPreferencias;
import com.uniride.model.Usuario;
import com.uniride.repository.NotificacionRepository;
import com.uniride.repository.NotificacionPreferenciasRepository;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service @RequiredArgsConstructor
public class NotificacionOrchestrator {
    private final NotificacionRepository notifRepo;
    private final NotificacionPreferenciasRepository prefRepo;
    private final UsuarioRepository usuarioRepo;

    public List<Long> createForUser(
            Long usuarioId,
            String tipo,
            String titulo,
            String cuerpo,
            String canalOverride,
            OffsetDateTime scheduledAt,
            //String payloadJson
            Map<String, Object> payloadJson
    ) {
        Usuario user = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        NotificacionPreferencias prefs = prefRepo.findByUsuario_Id(usuarioId)
                .orElseThrow(() -> new IllegalStateException("Preferencias no definidas para usuario: " + usuarioId));

        List<String> canales = new ArrayList<>();
        if (canalOverride != null) {
            canales.add(canalOverride);
        } else {
            if (Boolean.TRUE.equals(prefs.getInAppEnabled())) canales.add(TipoNotificacion.C_IN_APP);
            if (Boolean.TRUE.equals(prefs.getEmailEnabled()))  canales.add(TipoNotificacion.C_EMAIL);
        }

        List<Long> ids = new ArrayList<>();
        for (String canal : canales) {
            Notificacion n = Notificacion.builder()
                    .usuario(user)
                    .tipo(tipo)
                    .canal(canal)
                    .titulo(titulo)
                    .cuerpo(cuerpo)
                    .payloadJson(payloadJson)
                    .scheduledAt(scheduledAt)
                    .status(TipoNotificacion.S_PENDING)
                    .build();
            ids.add(notifRepo.save(n).getId());
        }
        return ids;
    }
}

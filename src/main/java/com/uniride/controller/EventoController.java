package com.uniride.controller;

import com.uniride.model.Evento;
import com.uniride.model.enums.EventoTipo;
import com.uniride.repository.UsuarioRepository;
import com.uniride.repository.EventoRepository;
import com.uniride.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@PreAuthorize("hasAnyRole('CONDUCTOR', 'ADMIN')")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar un evento (inicio, recogida o fin)
    @PostMapping("/registrar")
    public Evento registrarEvento(@RequestBody Evento evento) {

        // ===== 1️⃣ Si el evento es RECOGIO_PASAJERO, genera descripción automática =====
        if (evento.getTipo() == EventoTipo.RECOGIO_PASAJERO) {
            if (evento.getPasajeroId() != null) {
                Usuario pasajero = usuarioRepository.findById(evento.getPasajeroId()).orElse(null);
                if (pasajero != null) {
                    evento.setDescripcion("Recogió al pasajero " + pasajero.getNombre());
                } else {
                    evento.setDescripcion("Recogió a un pasajero (ID " + evento.getPasajeroId() + ")");
                }
            }
        }

        // ===== 2️⃣ Si el evento es TERMINO_VIAJE, desactivar todos los eventos del mismo viaje =====
        if (evento.getTipo() == EventoTipo.TERMINO_VIAJE) {
            List<Evento> eventosDelViaje = eventoRepository.findByViajeIdOrderByFechaHoraAsc(evento.getViajeId());
            for (Evento e : eventosDelViaje) {
                e.setActivo(false);
            }
            eventoRepository.saveAll(eventosDelViaje);

            // Generar descripción si no viene
            if (evento.getDescripcion() == null || evento.getDescripcion().isBlank()) {
                evento.setDescripcion("El viaje #" + evento.getViajeId() + " ha finalizado.");
            }

            // También guardar este evento como inactivo
            evento.setActivo(false);
        }

        // ===== 3️⃣ Guardar el evento =====
        return eventoRepository.save(evento);
    }

    // Obtener todos los eventos de un viaje
    @GetMapping("/viaje/{viajeId}")
    public List<Evento> listarPorViaje(@PathVariable Long viajeId) {
        return eventoRepository.findByViajeIdOrderByFechaHoraAsc(viajeId);
    }
}

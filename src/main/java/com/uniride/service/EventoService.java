package com.uniride.service;

import com.uniride.model.Usuario;
import com.uniride.repository.UsuarioRepository;
import com.uniride.model.Evento;
import com.uniride.repository.EventoRepository;
import com.uniride.model.enums.EventoTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

 @Transactional
public Evento registrarEvento(Long viajeId, Long conductorId, Long pasajeroId, EventoTipo tipo, String descripcion) {
    // 1) Si es recogida: generar descripción con nombre del pasajero
    if (tipo == EventoTipo.RECOGIO_PASAJERO) {
        var pasajero = usuarioRepository.findById(pasajeroId).orElse(null);
        descripcion = (pasajero != null)
            ? "Recogió al pasajero " + pasajero.getNombre()
            : "Recogió a un pasajero (ID " + pasajeroId + ")";
    }

    // 2) Si termina viaje: desactivar TODO el historial del viaje
    if (tipo == EventoTipo.TERMINO_VIAJE) {
        var eventos = eventoRepository.findByViajeIdOrderByFechaHoraAsc(viajeId);
        for (var e : eventos) e.setActivo(false);
        eventoRepository.saveAll(eventos);

        if (descripcion == null || descripcion.isBlank()) {
            descripcion = "El viaje #" + viajeId + " ha finalizado.";
        }
    }

    // 3) Crear el evento actual
    var evento = new Evento(viajeId, conductorId, pasajeroId, tipo, descripcion);

    // 👉 clave: si es TERMINO_VIAJE, guardarlo también como inactivo
    if (tipo == EventoTipo.TERMINO_VIAJE) {
        evento.setActivo(false);
    }

    return eventoRepository.save(evento);
}


    public List<Evento> listarPorViaje(Long viajeId) {
        return eventoRepository.findByViajeIdOrderByFechaHoraAsc(viajeId);
    }
}
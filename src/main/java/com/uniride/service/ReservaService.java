package com.uniride.service;

import com.uniride.dto.request.ReservaRequestDTO;
import com.uniride.dto.response.ReservaResponseDTO;
import com.uniride.exception.BusinessRuleException;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Pasajero;
import com.uniride.model.Viaje;
import com.uniride.model.ViajePasajero;
import com.uniride.model.ViajePasajeroId;
import com.uniride.model.enums.EstadoReserva;
import com.uniride.model.enums.MetodoPago;
import com.uniride.repository.PasajeroRepository;
import com.uniride.repository.ViajePasajeroRepository;
import com.uniride.repository.ViajeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ViajeRepository viajeRepository;
    private final PasajeroRepository pasajeroRepository;
    private final ViajePasajeroRepository viajePasajeroRepository;

    private void validarMetodoPago(String metodo) {
        // RN14: solo EFECTIVO, YAPE o PLIN
        try {
            MetodoPago mp = MetodoPago.valueOf(metodo.toUpperCase());
            if (mp != MetodoPago.EFECTIVO && mp != MetodoPago.YAPE && mp != MetodoPago.PLIN) {
                throw new BusinessRuleException("Método de pago no permitido. Use 'efectivo', 'yape' o 'plin'.");
            }
        } catch (IllegalArgumentException ex) {
            throw new BusinessRuleException("Método de pago inválido. Use 'efectivo', 'yape' o 'plin'.");
        }
    }

    @Transactional
    public ReservaResponseDTO reservar(@Valid ReservaRequestDTO dto) {
        // 1) Cargar entidades base
        Viaje viaje = viajeRepository.findById(dto.idViaje())
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado."));
        Pasajero pasajero = pasajeroRepository.findById(dto.idPasajero())
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado."));

        // 2) RN06: validar disponibilidad (no superar cupos)
        long reservasActuales = viajePasajeroRepository.countByViajeId(viaje.getId());
        if (reservasActuales >= viaje.getAsientosDisponibles()) {
            throw new BusinessRuleException("No hay asientos disponibles.");
        }

        // 3) Evitar duplicados
        if (viajePasajeroRepository.existsByViajeIdAndPasajeroId(viaje.getId(), pasajero.getId())) {
            throw new BusinessRuleException("El pasajero ya tiene una reserva en este viaje.");
        }

        // 4) RN14: validar método de pago (opcional, si lo envías)
        if (dto.metodoPago() != null && !dto.metodoPago().isBlank()) {
            validarMetodoPago(dto.metodoPago());
        }

        // 5) Crear relación reserva (clave compuesta)
        ViajePasajeroId id = new ViajePasajeroId(viaje.getId(), pasajero.getId());
        ViajePasajero vp = new ViajePasajero();
        vp.setId(id);
        vp.setViaje(viaje);
        vp.setPasajero(pasajero);

        // 6) Campos NOT NULL requeridos por la BD
        vp.setEstadoReserva(EstadoReserva.RESERVADO);     // Ajusta si tu enum usa otro nombre
        vp.setFechaReserva(LocalDateTime.now());          // Timestamp de creación

        // 7) (Opcional) Si tu entidad tiene el campo metodoPago, descomenta:
        // if (dto.metodoPago() != null && !dto.metodoPago().isBlank()) {
        //     vp.setMetodoPago(MetodoPago.valueOf(dto.metodoPago().toUpperCase()));
        // }

        // 8) Guardar
        viajePasajeroRepository.save(vp);

        // 9) Respuesta (DTO simple, sin relaciones → evita recursión)
        return ReservaResponseDTO.builder()
                .idViaje(viaje.getId())
                .idPasajero(pasajero.getId())
                .estado(vp.getEstadoReserva() != null ? vp.getEstadoReserva().name() : "RESERVADO")
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarPorViaje(Long viajeId) {
        if (!viajeRepository.existsById(viajeId)) {
            throw new ResourceNotFoundException("Viaje no encontrado.");
        }

        // Mapeamos a DTO para evitar ciclos JSON (Viaje<->ViajePasajero<->Pasajero)
        return viajePasajeroRepository.findByViajeId(viajeId).stream()
                .map(vp -> ReservaResponseDTO.builder()
                        .idViaje(vp.getViaje().getId())
                        .idPasajero(vp.getPasajero().getId())
                        .estado(vp.getEstadoReserva() != null ? vp.getEstadoReserva().name() : "RESERVADO")
                        .build()
                )
                .toList();
    }

    @Transactional
    public void cancelar(Long idViaje, Long idPasajero) {
        ViajePasajeroId id = new ViajePasajeroId(idViaje, idPasajero);
        ViajePasajero vp = viajePasajeroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada."));

        // Si deseas mantener histórico, puedes alternar a CANCELADA (si tu entidad lo soporta):
        // vp.setEstadoReserva(EstadoReserva.CANCELADA);
        // viajePasajeroRepository.save(vp);

        // Comportamiento actual: eliminar la reserva
        viajePasajeroRepository.delete(vp);
    }
}

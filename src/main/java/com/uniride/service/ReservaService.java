package com.uniride.service;

import com.uniride.dto.request.ReservaRequestDTO;
import com.uniride.dto.response.ReservaListadoDTO;
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
        Viaje viaje = viajeRepository.findById(dto.idViaje())
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado."));
        Pasajero pasajero = pasajeroRepository.findById(dto.idPasajero())
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado."));

        long reservasActuales = viajePasajeroRepository.countByViajeId(viaje.getId());
        if (reservasActuales >= viaje.getAsientosDisponibles()) {
            throw new BusinessRuleException("No hay asientos disponibles.");
        }

        if (viajePasajeroRepository.existsByViajeIdAndPasajeroId(viaje.getId(), pasajero.getId())) {
            throw new BusinessRuleException("El pasajero ya tiene una reserva en este viaje.");
        }

        if (dto.metodoPago() != null && !dto.metodoPago().isBlank()) {
            validarMetodoPago(dto.metodoPago());
        }

        ViajePasajeroId id = new ViajePasajeroId(viaje.getId(), pasajero.getId());
        ViajePasajero vp = new ViajePasajero();
        vp.setId(id);
        vp.setViaje(viaje);
        vp.setPasajero(pasajero);
        vp.setEstadoReserva(EstadoReserva.RESERVADO);
        vp.setFechaReserva(LocalDateTime.now());

        viajePasajeroRepository.save(vp);

        return ReservaResponseDTO.builder()
                .idViaje(viaje.getId())
                .idPasajero(pasajero.getId())
                .estado(vp.getEstadoReserva().name())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarPorViaje(Long viajeId) {
        if (!viajeRepository.existsById(viajeId)) {
            throw new ResourceNotFoundException("Viaje no encontrado.");
        }

        return viajePasajeroRepository.findByViajeId(viajeId).stream()
                .map(vp -> ReservaResponseDTO.builder()
                        .idViaje(vp.getViaje().getId())
                        .idPasajero(vp.getPasajero().getId())
                        .estado(vp.getEstadoReserva().name())
                        .build()
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservaListadoDTO> listarPorPasajero(Long pasajeroId) {
        if (!pasajeroRepository.existsById(pasajeroId)) {
            throw new ResourceNotFoundException("Pasajero no encontrado.");
        }

        return viajePasajeroRepository.findByPasajeroId(pasajeroId).stream()
                .map(vp -> ReservaListadoDTO.builder()
                        .idViaje(vp.getViaje().getId())
                        .idPasajero(vp.getPasajero().getId())
                        .nombrePasajero(vp.getPasajero().getUsuario().getNombre())
                        .correoPasajero(vp.getPasajero().getUsuario().getCorreoInstitucional())
                        .estadoReserva(vp.getEstadoReserva().name())
                        .fechaReserva(vp.getFechaReserva())
                        .build()
                )
                .toList();
    }

    @Transactional
    public void cancelar(Long idViaje, Long idPasajero) {
        ViajePasajeroId id = new ViajePasajeroId(idViaje, idPasajero);
        ViajePasajero vp = viajePasajeroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada."));

        viajePasajeroRepository.delete(vp);
    }
}
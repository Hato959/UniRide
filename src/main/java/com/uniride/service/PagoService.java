package com.uniride.service;

import com.uniride.dto.request.PagoRequestDTO;
import com.uniride.dto.response.PagoResponseDTO;
import com.uniride.model.Pago;
import com.uniride.model.ViajePasajero;
import com.uniride.model.ViajePasajeroId;
import com.uniride.model.enums.MetodoPago;
import com.uniride.repository.PagoRepository;
import com.uniride.repository.ViajePasajeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uniride.model.enums.EstadoPago;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagoService {
    private final PagoRepository pagoRepository;
    private final ViajePasajeroRepository viajePasajeroRepository;

    @Transactional
    public PagoResponseDTO crearPago(PagoRequestDTO dto) {
        MetodoPago metodoPago;
        try {
            metodoPago = (dto.metodo() == null || dto.metodo().isBlank())
                    ? MetodoPago.EFECTIVO
                    : MetodoPago.valueOf(dto.metodo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Método de pago inválido. Usa EFECTIVO, YAPE o PLIN.");
        }

        ViajePasajeroId vpId = new ViajePasajeroId(dto.idViaje(), dto.idPasajero());
        ViajePasajero viajePasajero = viajePasajeroRepository.findById(vpId)
                .orElseThrow(() -> new RuntimeException("Reserva (Viaje-Pasajero) no encontrada"));

        Pago pago = Pago.builder()
                .idConductor(dto.idConductor())
                .viajePasajero(viajePasajero)
                .monto(dto.monto())
                .metodo(metodoPago) // por defecto "EFECTIVO" si no se proporciona otro metodo
                .estado(EstadoPago.PENDIENTE) // Estado inicial "PENDIENTE"
                .fecha(LocalDateTime.now())
                .build();

        pagoRepository.save(pago);

        return new PagoResponseDTO(
                pago.getId(),
                pago.getIdConductor(),
                pago.getMonto().toString(),
                pago.getMetodo().name(),
                pago.getEstado().name(),
                pago.getViajePasajero().getViaje().getId(),
                pago.getViajePasajero().getPasajero().getId(),
                pago.getFecha()
        );
    }

    @Transactional
    public PagoResponseDTO actualizarEstadoPago(Long pagoId, PagoRequestDTO dto) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (dto.estado() != null && !dto.estado().isEmpty()) {
            try {
                EstadoPago nuevoEstado = EstadoPago.valueOf(dto.estado().toUpperCase());
                pago.setEstado(nuevoEstado);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado de pago inválido. Usa PENDIENTE o PAGADO.");
            }
        }

        pagoRepository.save(pago);

        return new PagoResponseDTO(
                pago.getId(),
                pago.getIdConductor(),
                pago.getMonto().toString(),
                pago.getMetodo().name(),
                pago.getEstado().name(),
                pago.getViajePasajero().getViaje().getId(),
                pago.getViajePasajero().getPasajero().getId(),
                pago.getFecha()
        );
    }
}

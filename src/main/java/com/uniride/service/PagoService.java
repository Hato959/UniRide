package com.uniride.service;

import com.uniride.dto.request.PagoRequestDTO;
import com.uniride.dto.response.PagoResponseDTO;
import com.uniride.model.Pago;
import com.uniride.model.ViajePasajero;
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
        String metodoPago = (dto.metodo() == null || dto.metodo().isEmpty()) ? MetodoPago.EFECTIVO : dto.metodo().toUpperCase();

        Pago pago = Pago.builder()
                .idConductor(dto.idConductor())
                .viajePasajeroId(dto.viajePasajeroId())
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
                pago.getMetodo(),
                pago.getEstado(),
                pago.getViajePasajeroId(),
                pago.getFecha()
        );
    }

    @Transactional
    public PagoResponseDTO actualizarEstadoPago(Long pagoId, PagoRequestDTO dto) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setEstado(dto.estado() != null ? dto.estado() : pago.getEstado()); // Actualiza el estado si se proporciona uno nuevo, si no, mantiene el actual
        pagoRepository.save(pago);

        return new PagoResponseDTO(
                pago.getId(),
                pago.getIdConductor(),
                pago.getMonto().toString(),
                pago.getMetodo(),
                pago.getEstado(),
                pago.getViajePasajeroId(),
                pago.getFecha()
        );
    }
}

package com.uniride.service;


import com.uniride.dto.request.TarifaSimbolicaRequestDTO;
import com.uniride.dto.response.TarifaSimbolicaResponseDTO;
import com.uniride.exception.BusinessRuleException;
import com.uniride.model.TarifaSimbolica;
import com.uniride.repository.TarifaSimbolicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.uniride.model.enums.MetodoPago;

@Service
@RequiredArgsConstructor
public class TarifaSimbolicaService {
    private final TarifaSimbolicaRepository tarifaSimbolicaRepository;

    @Transactional
    public TarifaSimbolicaResponseDTO establecer(TarifaSimbolicaRequestDTO dto) {
        MetodoPago metodoPago;
        try {
            metodoPago = (dto.metodoPago() == null || dto.metodoPago().isBlank())
                    ? MetodoPago.EFECTIVO
                    : MetodoPago.valueOf(dto.metodoPago().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("Método de pago inválido. Use: EFECTIVO, YAPE o PLIN.");
        }

        TarifaSimbolica tarifaSimbolica = TarifaSimbolica.builder()
                .montoTotal(dto.montoTotal())
                .numPasajeros(dto.numPasajeros())
                .conductorId(dto.conductorId())
                .vehiculoId(dto.vehiculoId())
                .precioPorPersona(dto.montoTotal() / dto.numPasajeros())
                .metodoPago(metodoPago)
                .fechaCreacion(LocalDateTime.now())
                .viajeId(dto.viajeId())
                .build();

        tarifaSimbolicaRepository.save(tarifaSimbolica);

        return new TarifaSimbolicaResponseDTO(
                tarifaSimbolica.getId(),
                tarifaSimbolica.getMontoTotal(),
                tarifaSimbolica.getNumPasajeros(),
                tarifaSimbolica.getConductorId(),
                tarifaSimbolica.getVehiculoId(),
                tarifaSimbolica.getPrecioPorPersona(),
                tarifaSimbolica.getMetodoPago().name(),
                tarifaSimbolica.getFechaCreacion(),
                tarifaSimbolica.getViajeId()
        );
    }

    @Transactional(readOnly = true)
    public TarifaSimbolicaResponseDTO obtenerDatos(Long id) {
        TarifaSimbolica tarifaSimbolica = tarifaSimbolicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa simbólica no encontrada"));

        return new TarifaSimbolicaResponseDTO(
                tarifaSimbolica.getId(),
                tarifaSimbolica.getMontoTotal(),
                tarifaSimbolica.getNumPasajeros(),
                tarifaSimbolica.getConductorId(),
                tarifaSimbolica.getVehiculoId(),
                tarifaSimbolica.getPrecioPorPersona(),
                tarifaSimbolica.getMetodoPago().name(),
                tarifaSimbolica.getFechaCreacion(),
                tarifaSimbolica.getViajeId()
        );
    }
}

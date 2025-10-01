package com.uniride.service;

import com.uniride.dto.request.ViajeRequestDTO;
import com.uniride.dto.response.ViajeResponseDTO;
import com.uniride.exception.BusinessRuleException;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Conductor;
import com.uniride.model.Viaje;
import com.uniride.repository.ConductorRepository;
import com.uniride.repository.ViajeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final ConductorRepository conductorRepository;

    // ==== Helpers ====
    private ViajeResponseDTO toDTO(Viaje v) {
        return ViajeResponseDTO.builder()
                .idViaje(v.getId())
                .idConductor(v.getConductor().getId())
                .nombreConductor(v.getConductor().getUsuario().getNombre()) // asume Conductor -> Usuario
                .origen(v.getOrigen())
                .destino(v.getDestino())
                .fecha(v.getFecha())
                .hora(v.getHora())
                .asientosDisponibles(v.getAsientosDisponibles())
                .recurrente(v.getRecurrente())
                .build();
    }

    private void validarPublicacion(@Valid ViajeRequestDTO dto) {
        if (dto.asientosDisponibles() == null || dto.asientosDisponibles() < 1) {
            throw new BusinessRuleException("Debe haber al menos 1 asiento disponible.");
        }
        if (dto.fecha() == null) {
            throw new BusinessRuleException("La fecha es obligatoria.");
        }
        if (dto.fecha().isBefore(LocalDate.now())) {
            throw new BusinessRuleException("La fecha del viaje debe ser futura.");
        }
        if (dto.hora() == null) {
            throw new BusinessRuleException("La hora es obligatoria.");
        }
        if (dto.origen() == null || dto.origen().isBlank()
                || dto.destino() == null || dto.destino().isBlank()) {
            throw new BusinessRuleException("Origen y destino son obligatorios.");
        }
        if (dto.idConductor() == null) {
            throw new BusinessRuleException("El id del conductor es obligatorio.");
        }
    }

    // ==== Casos de uso ====

    @Transactional
    public ViajeResponseDTO publicarViaje(ViajeRequestDTO dto) {
        validarPublicacion(dto);

        Conductor conductor = conductorRepository.findById(dto.idConductor())
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado."));

        Viaje viaje = Viaje.builder()
                .conductor(conductor)
                .origen(dto.origen())
                .destino(dto.destino())
                .fecha(dto.fecha())
                .hora(dto.hora())
                .asientosDisponibles(dto.asientosDisponibles())
                .recurrente(Boolean.TRUE.equals(dto.recurrente()))
                .build();

        viajeRepository.save(viaje);
        return toDTO(viaje);
    }

    @Transactional(readOnly = true)
    public ViajeResponseDTO obtenerPorId(Long id) {
        Viaje v = viajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado."));
        return toDTO(v);
    }

    @Transactional(readOnly = true)
    public List<ViajeResponseDTO> listarViajes() {
        return viajeRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional
    public ViajeResponseDTO editarViaje(Long id, ViajeRequestDTO dto) {
        Viaje v = viajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado."));

        // Cambiar conductor (opcional)
        if (dto.idConductor() != null && !dto.idConductor().equals(v.getConductor().getId())) {
            Conductor c = conductorRepository.findById(dto.idConductor())
                    .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado."));
            v.setConductor(c);
        }

        if (dto.origen() != null && !dto.origen().isBlank()) v.setOrigen(dto.origen());
        if (dto.destino() != null && !dto.destino().isBlank()) v.setDestino(dto.destino());
        if (dto.fecha() != null) {
            if (dto.fecha().isBefore(LocalDate.now())) {
                throw new BusinessRuleException("La fecha del viaje debe ser futura.");
            }
            v.setFecha(dto.fecha());
        }
        if (dto.hora() != null) v.setHora(dto.hora());
        if (dto.asientosDisponibles() != null) {
            if (dto.asientosDisponibles() < 1) {
                throw new BusinessRuleException("Debe haber al menos 1 asiento disponible.");
            }
            v.setAsientosDisponibles(dto.asientosDisponibles());
        }
        if (dto.recurrente() != null) v.setRecurrente(dto.recurrente());

        viajeRepository.save(v);
        return toDTO(v);
    }

    @Transactional
    public void eliminarViaje(Long id) {
        if (!viajeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Viaje no encontrado.");
        }
        viajeRepository.deleteById(id);
    }
}

package com.uniride.service;
import com.uniride.dto.request.VehiculoRegisterRequestDTO;
import com.uniride.dto.response.VehiculoResponseDTO;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Conductor;
import com.uniride.model.Vehiculo;
import com.uniride.repository.ConductorRepository;
import com.uniride.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class VehiculoService {
    private final VehiculoRepository vehiculoRepo;
    private final ConductorRepository conductorRepo;

    @Transactional
    public VehiculoResponseDTO registrar(VehiculoRegisterRequestDTO dto) {
        Conductor conductor = conductorRepo.findById(dto.conductorId())
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado"));

        Vehiculo vehiculo = Vehiculo.builder()
                .conductor(conductor)
                .marca(dto.marca())
                .placa(dto.placa())
                .modelo(dto.modelo())
                .color(dto.color())
                .build();

        vehiculoRepo.save(vehiculo);

        return new VehiculoResponseDTO(
                vehiculo.getId(),
                conductor.getId(),
                vehiculo.getMarca(),
                vehiculo.getPlaca(),
                vehiculo.getModelo(),
                vehiculo.getColor()
        );
    }

    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> listarPorConductor(Long conductorId) {
        return vehiculoRepo.findByConductorId(conductorId)
                .stream()
                .map(v -> new VehiculoResponseDTO(
                        v.getId(),
                        v.getConductor().getId(),
                        v.getMarca(),
                        v.getPlaca(),
                        v.getModelo(),
                        v.getColor()
                ))
                .toList();
    }

    private VehiculoResponseDTO toVehiculoResponse(Vehiculo v) {
        return VehiculoResponseDTO.builder()
                .id(v.getId())
                .marca(v.getMarca())
                .modelo(v.getModelo())
                .placa(v.getPlaca())
                .color(v.getColor())
                .build();
    }
}

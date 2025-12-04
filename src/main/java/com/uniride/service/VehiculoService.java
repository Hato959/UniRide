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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class VehiculoService {
    private final VehiculoRepository vehiculoRepo;
    private final ConductorRepository conductorRepo;
    private final CloudinaryService cloudinaryService;

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
                vehiculo.getColor(),
                vehiculo.getFotoVehiculoUrl()
        );
    }
    @Transactional
    public VehiculoResponseDTO actualizar(Long id, VehiculoRegisterRequestDTO dto) {
        Vehiculo vehiculo = vehiculoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        if (!dto.conductorId().equals(vehiculo.getConductor().getId())) {
            throw new ResourceNotFoundException("No puede cambiar el conductor asignado al vehículo.");
        }

        vehiculo.setMarca(dto.marca());
        vehiculo.setPlaca(dto.placa());
        vehiculo.setModelo(dto.modelo());
        vehiculo.setColor(dto.color());

        vehiculoRepo.save(vehiculo);

        return new VehiculoResponseDTO(
                vehiculo.getId(),
                vehiculo.getConductor().getId(),
                vehiculo.getMarca(),
                vehiculo.getPlaca(),
                vehiculo.getModelo(),
                vehiculo.getColor(),
                vehiculo.getFotoVehiculoUrl()
        );
    }

    @Transactional
    public VehiculoResponseDTO subirFoto(Long vehiculoId, MultipartFile file) {
        Vehiculo vehiculo = vehiculoRepo.findById(vehiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        try {
            // 1. Subir a Cloudinary en la carpeta "uniride/vehiculos"
            String url = cloudinaryService.uploadFile(file, "uniride/vehiculos");

            // 2. Actualizar el campo en la entidad
            vehiculo.setFotoVehiculoUrl(url);
            vehiculoRepo.save(vehiculo);

            // 3. Devolver el DTO actualizado
            return new VehiculoResponseDTO(
                    vehiculo.getId(),
                    vehiculo.getConductor().getId(),
                    vehiculo.getMarca(),
                    vehiculo.getPlaca(),
                    vehiculo.getModelo(),
                    vehiculo.getColor(),
                    vehiculo.getFotoVehiculoUrl() // Incluir la nueva URL
            );
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen del vehículo: " + e.getMessage(), e);
        }
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
                        v.getColor(),
                        v.getFotoVehiculoUrl()
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
                .fotoVehiculoUrl(v.getFotoVehiculoUrl())
                .build();
    }
}

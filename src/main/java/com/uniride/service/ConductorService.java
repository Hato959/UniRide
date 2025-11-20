package com.uniride.service;
import com.uniride.dto.request.ConductorRegisterRequestDTO;
import com.uniride.dto.response.ConductorInfoResponseDTO;
import com.uniride.dto.response.ConductorResponseDTO;
import com.uniride.exception.BusinessRuleException;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Conductor;
import com.uniride.model.Usuario;
import com.uniride.model.enums.RolActivo;
import com.uniride.repository.ConductorRepository;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConductorService {
    private final ConductorRepository conductorRepo;
    private final UsuarioRepository usuarioRepo;

    @Transactional
    public ConductorResponseDTO registrar(ConductorRegisterRequestDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (conductorRepo.findByUsuarioId(usuario.getId()).isPresent()) {
            throw new BusinessRuleException("Este usuario ya tiene un perfil de conductor");
        }
        if(conductorRepo.existsByUsuarioId(usuario.getId())) {
            throw new BusinessRuleException("El usuario ya esta registrado como conductor");
        }

        Conductor conductor = Conductor.builder()
                .usuario(usuario)
                .licenciaConducir(dto.licenciaConducir())
                .experienciaAnios(dto.experienciaAnios())
                .build();

        conductorRepo.save(conductor);
        usuario.setRolActivo(RolActivo.CONDUCTOR);
        usuarioRepo.save(usuario);

        return new ConductorResponseDTO(
                conductor.getId(),
                usuario.getId(),
                conductor.getLicenciaConducir(),
                conductor.getExperienciaAnios()
        );
    }

    @Transactional(readOnly = true)
    public ConductorResponseDTO obtenerPerfil(Long usuarioId) {
        Conductor conductor = conductorRepo.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado"));

        return new ConductorResponseDTO(
                conductor.getId(),
                conductor.getUsuario().getId(),
                conductor.getLicenciaConducir(),
                conductor.getExperienciaAnios()
        );
    }

    @Transactional
    public ConductorResponseDTO actualizar(Long usuarioId, ConductorRegisterRequestDTO dto) {
        Conductor conductor = conductorRepo.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado"));

        conductor.setLicenciaConducir(dto.licenciaConducir());
        conductor.setExperienciaAnios(dto.experienciaAnios());

        conductorRepo.save(conductor);

        return new ConductorResponseDTO(
                conductor.getId(),
                conductor.getUsuario().getId(),
                conductor.getLicenciaConducir(),
                conductor.getExperienciaAnios()
        );
    }

    @Transactional
    public void eliminar(Long usuarioId) {
        Conductor conductor = conductorRepo.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado"));

        conductorRepo.delete(conductor);
    }

    @Transactional
    public ConductorInfoResponseDTO infoDetallada(Long conductorId) {
        Conductor conductor = conductorRepo.findByUsuarioId(conductorId)
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado"));

        return new ConductorInfoResponseDTO(
                conductor.getId(),
                conductor.getUsuario().getId(),
                conductor.getUsuario().getNombre(),
                conductor.getUsuario().getCorreoInstitucional(),
                conductor.getUsuario().getCarrera(),
                conductor.getUsuario().getDistrito(),
                conductor.getUsuario().getDni(),
                conductor.getLicenciaConducir(),
                conductor.getExperienciaAnios()
        );
    }

    // --- mappers ---
    private ConductorResponseDTO toConductorResponse(Conductor c) {
        //List<VehiculoResponseDTO> vehiculos = c.getVehiculos() == null ? List.of()
        //        : c.getVehiculos().stream().toList();

        return ConductorResponseDTO.builder()
                .id(c.getId())
                //.nombreUsuario(c.getUsuario().getNombre())
                //.correoInstitucional(c.getUsuario().getCorreoInstitucional()) // corrige si tu campo tiene un typo
                .licenciaConducir(c.getLicenciaConducir())
                .experienciaAnios(c.getExperienciaAnios())
                //.vehiculos(vehiculos)
                .build();
    }
}

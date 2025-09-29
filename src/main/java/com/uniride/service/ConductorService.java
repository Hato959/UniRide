package com.uniride.service;
import com.uniride.dto.request.ConductorRegisterRequestDTO;
import com.uniride.dto.response.ConductorResponseDTO;
import com.uniride.exception.BusinessRuleException;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Conductor;
import com.uniride.model.Usuario;
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

        Conductor conductor = Conductor.builder()
                .usuario(usuario)
                .licenciaConducir(dto.licenciaConducir())
                .experienciaAnios(dto.experienciaAnios())
                .build();

        conductorRepo.save(conductor);

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
}

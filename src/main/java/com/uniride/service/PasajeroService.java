package com.uniride.service;

import com.uniride.dto.request.PasajeroRequestDTO;
import com.uniride.dto.response.PasajeroResponseDTO;
import com.uniride.exception.BusinessRuleException;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Pasajero;
import com.uniride.model.Usuario;
import com.uniride.repository.PasajeroRepository;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasajeroService {
    private final PasajeroRepository pasajeroRepo;
    private final UsuarioRepository usuarioRepo;

    @Transactional
    public PasajeroResponseDTO registrar(PasajeroRequestDTO dto) {
        if (pasajeroRepo.existsByUsuarioId(dto.usuarioId())) {
            throw new BusinessRuleException("Este usuario ya tiene perfil de pasajero.");
        }

        Usuario usuario = usuarioRepo.findById(dto.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Pasajero pasajero = Pasajero.builder()
                .usuario(usuario)
                .preferencias(dto.preferencias())
                .build();

        pasajeroRepo.save(pasajero);

        return new PasajeroResponseDTO(
                pasajero.getId(),
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoInstitucional(),
                pasajero.getPreferencias()
        );
    }

    @Transactional(readOnly = true)
    public PasajeroResponseDTO obtener(Long id) {
        Pasajero pasajero = pasajeroRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado"));

        return new PasajeroResponseDTO(
                pasajero.getId(),
                pasajero.getUsuario().getId(),
                pasajero.getUsuario().getNombre(),
                pasajero.getUsuario().getCorreoInstitucional(),
                pasajero.getPreferencias()
        );
    }

    @Transactional
    public PasajeroResponseDTO actualizar(Long id, PasajeroRequestDTO dto) {
        Pasajero pasajero = pasajeroRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pasajero no encontrado"));

        pasajero.setPreferencias(dto.preferencias());
        pasajeroRepo.save(pasajero);

        return new PasajeroResponseDTO(
                pasajero.getId(),
                pasajero.getUsuario().getId(),
                pasajero.getUsuario().getNombre(),
                pasajero.getUsuario().getCorreoInstitucional(),
                pasajero.getPreferencias()
        );
    }

    @Transactional
    public void eliminar(Long id) {
        if (!pasajeroRepo.existsById(id)) {
            throw new ResourceNotFoundException("Pasajero no encontrado");
        }
        pasajeroRepo.deleteById(id);
    }
}

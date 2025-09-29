package com.uniride.service;

import com.uniride.dto.request.UsuarioRegisterRequestDTO;
import com.uniride.dto.response.UsuarioResponseDTO;
import com.uniride.exception.BusinessRuleException;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Usuario;
import com.uniride.model.enums.RolActivo;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // inyectamos el encoder

    @Transactional
    public UsuarioResponseDTO registrar(UsuarioRegisterRequestDTO dto) {
        if (usuarioRepository.existsByCorreoInsitucional(dto.correoInsitucional())) {
            throw new BusinessRuleException("El correo institucional ya está registrado.");
        }
        if (usuarioRepository.existsByDni(dto.dni())) {
            throw new BusinessRuleException("El DNI ya está registrado.");
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.nombre())
                .correoInsitucional(dto.correoInsitucional())
                .contrasena(passwordEncoder.encode(dto.contrasena())) // encriptamos la contraseña
                .carrera(dto.carrera())
                .distrito(dto.distrito())
                .dni(dto.dni())
                .verificado(false)
                .build();

        usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoInsitucional(),
                usuario.getCarrera(),
                usuario.getDistrito(),
                usuario.getDni(),
                usuario.getVerificado()
        );
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPerfil(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoInsitucional(),
                usuario.getCarrera(),
                usuario.getDistrito(),
                usuario.getDni(),
                usuario.getVerificado()
        );
    }

    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioRegisterRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setNombre(dto.nombre());
        usuario.setCorreoInsitucional(dto.correoInsitucional());
        usuario.setCarrera(dto.carrera());
        usuario.setDistrito(dto.distrito());
        // OJOOOOO: NO ACTUALIZAR CONTRASEÑA AQUI, solo en el endpoint de cambio de password

        usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoInsitucional(),
                usuario.getCarrera(),
                usuario.getDistrito(),
                usuario.getDni(),
                usuario.getVerificado()
        );
    }

    @Transactional
    public void cambiarPassword(Long id, String contrasenaActual, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Comparar la contraseña actual con el hash en la BD
        if (!passwordEncoder.matches(contrasenaActual, usuario.getContrasena())) {
            throw new BusinessRuleException("La contraseña actual no es válida");
        }

        // Guardar la nueva contraseña encriptada
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void cambiarRol(Long usuarioId, RolActivo nuevoRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validaciones
        if (nuevoRol == RolActivo.CONDUCTOR && usuario.getConductor() == null) {
            throw new BusinessRuleException("El usuario no tiene perfil de conductor.");
        }
        if (nuevoRol == RolActivo.PASAJERO && usuario.getPasajero() == null) {
            throw new BusinessRuleException("El usuario no tiene perfil de pasajero.");
        }

        usuario.setRolActivo(nuevoRol);
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public String obtenerRolActivo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return usuario.getRolActivo().name(); // devuelve "PASAJERO" o "CONDUCTOR"
    }
}

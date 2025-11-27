package com.uniride.service;

import com.uniride.dto.request.LoginRequest;
import com.uniride.dto.request.RegisterRequest;
import com.uniride.dto.response.AuthResponse;
import com.uniride.model.Usuario;
import com.uniride.model.Conductor;
import com.uniride.model.Pasajero;
import com.uniride.repository.UsuarioRepository;
import com.uniride.repository.ConductorRepository;
import com.uniride.repository.PasajeroRepository;
import com.uniride.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final ConductorRepository conductorRepository; // <--- INYECTAR
    private final PasajeroRepository pasajeroRepository;   // <--- INYECTAR
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 🔹 Registro de usuario nuevo
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.findByCorreo(request.correoInstitucional()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.nombre())
                .correoInstitucional(request.correoInstitucional())
                .contrasena(passwordEncoder.encode(request.contrasena()))
                .carrera(request.carrera())
                .distrito(request.distrito())
                .dni(request.dni())
                .verificado(false)
                .rolActivo(request.rolActivo())
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        String token = jwtUtil.generateToken(
                usuarioGuardado.getId(),
                usuarioGuardado.getCorreoInstitucional(),
                usuarioGuardado.getRolActivo().name()
        );

        // Al registrarse, aun no tiene perfil de conductor ni pasajero, enviamos null
        return new AuthResponse(
                token,
                usuarioGuardado.getId(), // usuarioId
                null, // conductorId
                null, // pasajeroId
                usuarioGuardado.getNombre(),
                usuarioGuardado.getRolActivo().name(),
                usuarioGuardado.getCorreoInstitucional()
        );
    }

    // 🔹 Login (autenticación) CORREGIDO
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.correo(),
                        request.contrasena()
                )
        );

        Usuario usuario = usuarioRepository.findByCorreo(request.correo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        String token = jwtUtil.generateToken(
                usuario.getId(),
                usuario.getCorreoInstitucional(),
                usuario.getRolActivo().name()
        );

        Long conductorId = conductorRepository.findByUsuarioId(usuario.getId())
                .map(Conductor::getId)
                .orElse(null);

        Long pasajeroId = pasajeroRepository.findByUsuarioId(usuario.getId())
                .map(Pasajero::getId)
                .orElse(null);


        return new AuthResponse(
                token,
                usuario.getId(),    // usuarioId
                conductorId,        // conductorId (puede ser null)
                pasajeroId,         // pasajeroId (puede ser null)
                usuario.getNombre(),
                usuario.getRolActivo().name(),
                usuario.getCorreoInstitucional()
        );
    }
}
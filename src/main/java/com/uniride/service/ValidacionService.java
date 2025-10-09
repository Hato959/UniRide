package com.uniride.service;

import com.uniride.exception.BusinessRuleException;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.CodigoVerificacion;
import com.uniride.repository.CodigoVerificacionRepository;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ValidacionService {
    private final CodigoVerificacionRepository codigoRepo;
    private final UsuarioRepository usuarioRepo;
    private final EmailService emailService;

    @Transactional
    public void enviarCodigo(String correo) {
        if (!correo.endsWith("@upc.edu.pe")) {
            throw new BusinessRuleException("Solo se aceptan correos institucionales (@upc.edu.pe)");
        }

        String codigo = String.format("%06d", new Random().nextInt(999999)); // 6 dígitos
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(10);

        CodigoVerificacion cv = CodigoVerificacion.builder()
                .correo(correo)
                .codigo(codigo)
                .expiracion(expiracion)
                .usado(false)
                .build();

        codigoRepo.save(cv);

        emailService.enviarCorreo(
                correo,
                "Código de verificación - UniRide",
                "Tu código de verificación es: " + codigo + "\nEste código caduca en 10 minutos."
        );
    }

    @Transactional
    public void validarUsuarioCompleto(Long usuarioId, String dni, String codigo) {
        var usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar DNI
        if (!usuario.getDni().equals(dni)) {
            throw new BusinessRuleException("El DNI ingresado no coincide con el registrado.");
        }

        // Validar código
        CodigoVerificacion cv = codigoRepo.findByCorreoAndCodigoAndUsadoFalse(usuario.getCorreoInstitucional(), codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Código inválido o ya usado."));

        if (cv.getExpiracion().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("El código ha expirado.");
        }

        // Marcar código como usado
        cv.setUsado(true);
        codigoRepo.save(cv);

        // Si ambas pasan → marcar usuario como verificado
        usuario.setVerificado(true);
        usuarioRepo.save(usuario);
    }
}

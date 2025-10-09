package com.uniride.controller;

import com.uniride.dto.request.ValidacionUsuarioRequestDTO;
import com.uniride.service.ValidacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validacion")
@RequiredArgsConstructor
public class ValidacionController {
    private final ValidacionService validacionService;

    @PostMapping("/enviar-codigo")
    public ResponseEntity<String> enviarCodigo(@RequestParam String correo) {
        validacionService.enviarCodigo(correo);
        return ResponseEntity.ok("Código enviado al correo institucional.");
    }

    @PostMapping("/verificar-usuario")
    public ResponseEntity<String> verificarUsuario(@RequestBody ValidacionUsuarioRequestDTO dto) {
        validacionService.validarUsuarioCompleto(dto.usuarioId(), dto.dni(), dto.codigoVerificacion());
        return ResponseEntity.ok("Usuario validado correctamente (DNI y correo).");
    }
}

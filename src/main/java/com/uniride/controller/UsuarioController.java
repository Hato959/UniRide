package com.uniride.controller;

import com.uniride.dto.request.CambiarPasswordRequestDTO;
import com.uniride.dto.request.UsuarioRegisterRequestDTO;
import com.uniride.dto.response.UsuarioResponseDTO;
import com.uniride.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    // Crear usuario
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRegisterRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.registrar(dto));
    }

    // Obtener perfil
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPerfil(id));
    }

    // Actualizar perfil
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRegisterRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

    // Cambiar contraseña
    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<String> cambiarPassword(
            @PathVariable Long id,
            @RequestBody CambiarPasswordRequestDTO dto) {
        usuarioService.cambiarPassword(id, dto.contrasenaActual(), dto.nuevaContrasena());
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}

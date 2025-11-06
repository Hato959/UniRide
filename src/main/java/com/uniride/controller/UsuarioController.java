package com.uniride.controller;

import com.uniride.dto.request.CambiarPasswordRequestDTO;
import com.uniride.dto.request.UsuarioRegisterRequestDTO;
import com.uniride.dto.response.UsuarioResponseDTO;
import com.uniride.service.UsuarioService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioRegisterRequestDTO dto) {
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

    @PutMapping("/{id}/cambiar-rol")
    public ResponseEntity<String> cambiarRol(
            @PathVariable Long id,
            @RequestParam String nuevoRol) {
        usuarioService.cambiarRol(id, nuevoRol);
        return ResponseEntity.ok("Rol activo cambiado a " + nuevoRol);
    }

    // Obtener el rol activo del usuario
    @GetMapping("/{id}/rol-activo")
    public ResponseEntity<String> obtenerRolActivo(@PathVariable Long id) {
        String rol = usuarioService.obtenerRolActivo(id);
        return ResponseEntity.ok("El rol activo del usuario es: " + rol);
    }
}

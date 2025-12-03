package com.uniride.controller;

import com.uniride.dto.request.CambiarPasswordRequestDTO;
import com.uniride.dto.request.UsuarioRegisterRequestDTO;
import com.uniride.dto.response.UsuarioResponseDTO;
import com.uniride.model.enums.RolActivo;
import com.uniride.service.FotoPerfilService;
import com.uniride.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final FotoPerfilService fotoPerfilService;

    // Crear usuario
    @PostMapping("/registro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRegisterRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.registrar(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONDUCTOR', 'PASAJERO')")
    public ResponseEntity<UsuarioResponseDTO> obtenerPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPerfil(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONDUCTOR', 'PASAJERO')")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioRegisterRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

    @PutMapping("/{id}/cambiar-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> cambiarPassword(@PathVariable Long id, @RequestBody CambiarPasswordRequestDTO dto) {
        usuarioService.cambiarPassword(id, dto.contrasenaActual(), dto.nuevaContrasena());
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }

    @PutMapping("/{id}/cambiar-rol")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONDUCTOR', 'PASAJERO')")
    public ResponseEntity<String> cambiarRol(@PathVariable Long id, @RequestParam RolActivo nuevoRol) {
        usuarioService.cambiarRol(id, nuevoRol);
        return ResponseEntity.ok("Rol activo cambiado a " + nuevoRol);
    }

    // Obtener el rol activo del usuario
    @GetMapping("/{id}/rol-activo")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> obtenerRolActivo(@PathVariable Long id) {
        String rol = usuarioService.obtenerRolActivo(id);
        return ResponseEntity.ok("El rol activo del usuario es: " + rol);
    }

    @PostMapping("/{id}/foto-perfil")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponseDTO> subirFotoPerfil(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        UsuarioResponseDTO updatedUser = fotoPerfilService.subirYActualizarUsuario(id, file);
        return ResponseEntity.ok(updatedUser);
    }
}

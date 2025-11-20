package com.uniride.controller;
import com.uniride.dto.request.ConductorRegisterRequestDTO;
import com.uniride.dto.response.ConductorInfoResponseDTO;
import com.uniride.dto.response.ConductorResponseDTO;
import com.uniride.service.ConductorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conductores")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ConductorController {
    private final ConductorService conductorService;

    @PostMapping("/registro")
    @PreAuthorize("hasAnyRole('ADMIN', 'PASAJERO', 'CONDUCTOR')")
    public ResponseEntity<ConductorResponseDTO> registrar(@RequestBody ConductorRegisterRequestDTO dto) {
        return ResponseEntity.ok(conductorService.registrar(dto));
    }

    @GetMapping("/{usuarioId}")
    @PreAuthorize("hasAnyRole('CONDUCTOR', 'ADMIN')")
    public ResponseEntity<ConductorResponseDTO> obtenerPerfil(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(conductorService.obtenerPerfil(usuarioId));
    }

    @GetMapping("/detalles/{conductorId}")
    @PreAuthorize("hasAnyRole('CONDUCTOR', 'ADMIN')")
    public ResponseEntity<ConductorInfoResponseDTO> infoDetallada(@PathVariable Long conductorId) {
        return ResponseEntity.ok(conductorService.infoDetallada(conductorId));
    }
    @PutMapping("/{usuarioId}")
    @PreAuthorize("hasAnyRole('CONDUCTOR', 'ADMIN')")
    public ResponseEntity<ConductorResponseDTO> actualizar(
            @PathVariable Long usuarioId,
            @RequestBody ConductorRegisterRequestDTO dto) {
        return ResponseEntity.ok(conductorService.actualizar(usuarioId, dto));
    }

    @DeleteMapping("/{usuarioId}")
    @PreAuthorize("hasAnyRole('CONDUCTOR', 'ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable Long usuarioId) {
        conductorService.eliminar(usuarioId);
        return ResponseEntity.ok("Perfil de conductor eliminado correctamente.");
    }
}

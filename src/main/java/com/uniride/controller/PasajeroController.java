package com.uniride.controller;

import com.uniride.dto.request.PasajeroRequestDTO;
import com.uniride.dto.response.PasajeroResponseDTO;
import com.uniride.service.PasajeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pasajeros")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class PasajeroController {
    private final PasajeroService pasajeroService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PASAJERO', 'CONDUCTOR')")
    public ResponseEntity<PasajeroResponseDTO> registrar(@RequestBody PasajeroRequestDTO dto) {
        return ResponseEntity.ok(pasajeroService.registrar(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PASAJERO', 'ADMIN')")
    public ResponseEntity<PasajeroResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(pasajeroService.obtener(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PASAJERO', 'ADMIN')")
    public ResponseEntity<PasajeroResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody PasajeroRequestDTO dto) {
        return ResponseEntity.ok(pasajeroService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PASAJERO', 'ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        pasajeroService.eliminar(id);
        return ResponseEntity.ok("Perfil de pasajero eliminado correctamente.");
    }
}

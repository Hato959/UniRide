package com.uniride.controller;

import com.uniride.dto.request.PasajeroRequestDTO;
import com.uniride.dto.response.PasajeroResponseDTO;
import com.uniride.service.PasajeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pasajeros")
@RequiredArgsConstructor
public class PasajeroController {
    private final PasajeroService pasajeroService;

    @PostMapping
    public ResponseEntity<PasajeroResponseDTO> registrar(@RequestBody PasajeroRequestDTO dto) {
        return ResponseEntity.ok(pasajeroService.registrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasajeroResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(pasajeroService.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasajeroResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody PasajeroRequestDTO dto) {
        return ResponseEntity.ok(pasajeroService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        pasajeroService.eliminar(id);
        return ResponseEntity.ok("Perfil de pasajero eliminado correctamente.");
    }
}

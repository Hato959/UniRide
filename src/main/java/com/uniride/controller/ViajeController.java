package com.uniride.controller;

import com.uniride.dto.request.ViajeRecurrenteRequestDTO;
import com.uniride.dto.request.ViajeRequestDTO;
import com.uniride.dto.response.ViajeRecurrenteResponseDTO;
import com.uniride.dto.response.ViajeResponseDTO;
import com.uniride.service.ViajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viajes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CONDUCTOR', 'ADMIN')")

public class ViajeController {

    private final ViajeService viajeService;

    // Crear / Publicar viaje  (análogo a POST /usuarios/registro)
    @PostMapping("/publicar")
    public ResponseEntity<ViajeResponseDTO> publicar(@Valid @RequestBody ViajeRequestDTO dto) {
        return ResponseEntity.ok(viajeService.publicarViaje(dto));
    }

    // Obtener viaje por ID  (análogo a GET /usuarios/{id})
    @GetMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(viajeService.obtenerPorId(id));
    }

    // Listar viajes  (no tiene equivalente en UsuarioController, pero útil)
    @GetMapping
    public ResponseEntity<List<ViajeResponseDTO>> listar() {
        return ResponseEntity.ok(viajeService.listarViajes());
    }

    // Actualizar viaje  (análogo a PUT /usuarios/{id})
    @PutMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ViajeRequestDTO dto) {
        return ResponseEntity.ok(viajeService.editarViaje(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        viajeService.eliminarViaje(id);
        return ResponseEntity.ok("Viaje eliminado correctamente.");
    }

       @PostMapping("/recurrente")
    public ResponseEntity<ViajeRecurrenteResponseDTO> programarRecurrente(
            @Valid @RequestBody ViajeRecurrenteRequestDTO dto) {
        return ResponseEntity.ok(viajeService.programarViajesRecurrentes(dto));
    }
}

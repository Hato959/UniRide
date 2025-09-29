package com.uniride.controller;
import com.uniride.dto.request.ConductorRegisterRequestDTO;
import com.uniride.dto.response.ConductorResponseDTO;
import com.uniride.service.ConductorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conductores")
@RequiredArgsConstructor
public class ConductorController {
    private final ConductorService conductorService;

    @PostMapping("/registro")
    public ResponseEntity<ConductorResponseDTO> registrar(@RequestBody ConductorRegisterRequestDTO dto) {
        return ResponseEntity.ok(conductorService.registrar(dto));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<ConductorResponseDTO> obtenerPerfil(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(conductorService.obtenerPerfil(usuarioId));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<ConductorResponseDTO> actualizar(
            @PathVariable Long usuarioId,
            @RequestBody ConductorRegisterRequestDTO dto) {
        return ResponseEntity.ok(conductorService.actualizar(usuarioId, dto));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> eliminar(@PathVariable Long usuarioId) {
        conductorService.eliminar(usuarioId);
        return ResponseEntity.ok("Perfil de conductor eliminado correctamente.");
    }
}

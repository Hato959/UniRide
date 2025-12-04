package com.uniride.controller;
import com.uniride.dto.request.VehiculoRegisterRequestDTO;
import com.uniride.dto.response.VehiculoResponseDTO;
import com.uniride.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CONDUCTOR', 'ADMIN')")
public class VehiculoController {
    private final VehiculoService vehiculoService;

    @PostMapping("/registro")
    public ResponseEntity<VehiculoResponseDTO> registrar(@RequestBody VehiculoRegisterRequestDTO dto) {
        return ResponseEntity.ok(vehiculoService.registrar(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody VehiculoRegisterRequestDTO dto) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, dto));
    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<VehiculoResponseDTO> subirFoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        VehiculoResponseDTO updatedVehicle = vehiculoService.subirFoto(id, file);
        return ResponseEntity.ok(updatedVehicle);
    }

    @GetMapping("/conductor/{conductorId}")
    public ResponseEntity<List<VehiculoResponseDTO>> listarPorConductor(@PathVariable Long conductorId) {
        return ResponseEntity.ok(vehiculoService.listarPorConductor(conductorId));
    }
}

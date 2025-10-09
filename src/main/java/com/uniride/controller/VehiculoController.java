package com.uniride.controller;
import com.uniride.dto.request.VehiculoRegisterRequestDTO;
import com.uniride.dto.response.VehiculoResponseDTO;
import com.uniride.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {
    private final VehiculoService vehiculoService;

    @PostMapping("/registro")
    public ResponseEntity<VehiculoResponseDTO> registrar(@RequestBody VehiculoRegisterRequestDTO dto) {
        return ResponseEntity.ok(vehiculoService.registrar(dto));
    }

    @GetMapping("/conductor/{conductorId}")
    public ResponseEntity<List<VehiculoResponseDTO>> listarPorConductor(@PathVariable Long conductorId) {
        return ResponseEntity.ok(vehiculoService.listarPorConductor(conductorId));
    }
}

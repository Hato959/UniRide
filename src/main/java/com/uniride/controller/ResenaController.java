package com.uniride.controller;

import com.uniride.dto.request.ResenaRequestDTO;
import com.uniride.dto.response.ResenaResponseDTO;
import com.uniride.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resenas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('PASAJERO', 'ADMIN')")
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping
    public ResponseEntity<ResenaResponseDTO> registrar(@RequestBody ResenaRequestDTO dto) {
        return ResponseEntity.ok(resenaService.registrar(dto));
    }

    @GetMapping("/viaje/{viajeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ResenaResponseDTO>> listarPorViaje(@PathVariable Long viajeId) {
        return ResponseEntity.ok(resenaService.listarPorViaje(viajeId));
    }
}

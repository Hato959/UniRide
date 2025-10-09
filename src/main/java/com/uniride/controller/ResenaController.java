package com.uniride.controller;

import com.uniride.dto.request.ResenaRequestDTO;
import com.uniride.dto.response.ResenaResponseDTO;
import com.uniride.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping
    public ResponseEntity<ResenaResponseDTO> registrar(@RequestBody ResenaRequestDTO dto) {
        return ResponseEntity.ok(resenaService.registrar(dto));
    }

    @GetMapping("/viaje/{viajeId}")
    public ResponseEntity<List<ResenaResponseDTO>> listarPorViaje(@PathVariable Long viajeId) {
        return ResponseEntity.ok(resenaService.listarPorViaje(viajeId));
    }
}

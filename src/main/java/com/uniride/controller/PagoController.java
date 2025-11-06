package com.uniride.controller;

import com.uniride.dto.request.PagoRequestDTO;
import com.uniride.dto.response.PagoResponseDTO;
import com.uniride.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    // Crear pago
    @PostMapping("/crear")
    public ResponseEntity<PagoResponseDTO> crearPago(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.crearPago(dto));
    }

    // Actualizar estado del pago
    @PutMapping("{id}")
    public ResponseEntity<PagoResponseDTO> actualizarPago(
            @PathVariable Long id,
            @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.actualizarEstadoPago(id, dto));
    }
}

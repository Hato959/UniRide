package com.uniride.controller;

import com.uniride.dto.request.ReservaRequestDTO;
import com.uniride.dto.response.ReservaResponseDTO;
import com.uniride.model.ViajePasajero;
import com.uniride.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // Crear reserva
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> reservar(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(reservaService.reservar(dto));
    }

    // Listar reservas por viaje
    @GetMapping("/viaje/{idViaje}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorViaje(@PathVariable Long idViaje) {
        return ResponseEntity.ok(reservaService.listarPorViaje(idViaje));
    }

    // Cancelar reserva (por clave compuesta viaje+pasajero)
    @DeleteMapping
    public ResponseEntity<String> cancelar(@RequestParam Long idViaje, @RequestParam Long idPasajero) {
        reservaService.cancelar(idViaje, idPasajero);
        return ResponseEntity.ok("Reserva cancelada.");
    }
}

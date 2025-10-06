package com.uniride.controller;

import com.uniride.dto.request.TarifaSimbolicaRequestDTO;
import com.uniride.dto.response.TarifaSimbolicaResponseDTO;
import com.uniride.model.TarifaSimbolica;
import com.uniride.service.TarifaSimbolicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarifas-simbolicas")
@RequiredArgsConstructor
public class TarifaSimbolicaController {
    private final TarifaSimbolicaService tarifaSimbolicaService;

    //Establecer tarifa simbólica
    @PostMapping("/establecer")
    public ResponseEntity<TarifaSimbolicaResponseDTO> establecer(@RequestBody TarifaSimbolicaRequestDTO dto){
        return ResponseEntity.ok(tarifaSimbolicaService.establecer(dto));
    }

    // Obtener datos de tarifa simbólica
    @GetMapping("/{id}")
    public ResponseEntity<TarifaSimbolicaResponseDTO> obtenerTarifaSimbolica(@PathVariable Long id){
        return ResponseEntity.ok(tarifaSimbolicaService.obtenerDatos(id));
    }
}

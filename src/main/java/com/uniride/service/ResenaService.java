package com.uniride.service;

import com.uniride.dto.request.ResenaRequestDTO;
import com.uniride.dto.response.ResenaResponseDTO;

import java.util.List;

public interface ResenaService {
    ResenaResponseDTO registrar(ResenaRequestDTO dto);
    List<ResenaResponseDTO> listarPorViaje(Long viajeId);
}

package com.uniride.service.impl;

import com.uniride.dto.request.ResenaRequestDTO;
import com.uniride.dto.response.ResenaResponseDTO;
import com.uniride.model.Resena;
import com.uniride.model.Usuario;
import com.uniride.model.Viaje;
import com.uniride.repository.ResenaRepository;
import com.uniride.repository.UsuarioRepository;
import com.uniride.repository.ViajeRepository;
import com.uniride.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResenaServiceImpl implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final ViajeRepository viajeRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ResenaResponseDTO registrar(ResenaRequestDTO dto) {
        Viaje viaje = viajeRepository.findById(dto.getViajeId())
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));
        Usuario autor = usuarioRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
        Usuario destinatario = usuarioRepository.findById(dto.getDestinatarioId())
                .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

        Resena resena = Resena.builder()
                .viaje(viaje)
                .autor(autor)
                .destinatario(destinatario)
                .calificacion(dto.getCalificacion())
                .comentario(dto.getComentario())
                .build();

        resenaRepository.save(resena);

        return new ResenaResponseDTO(
                resena.getId(),
                resena.getViaje().getId(),
                resena.getAutor().getId(),
                resena.getDestinatario().getId(),
                resena.getCalificacion(),
                resena.getComentario()
        );
    }

    @Override
    public List<ResenaResponseDTO> listarPorViaje(Long viajeId) {
        return resenaRepository.findByViajeId(viajeId).stream()
                .map(resena -> new ResenaResponseDTO(
                        resena.getId(),
                        resena.getViaje().getId(),
                        resena.getAutor().getId(),
                        resena.getDestinatario().getId(),
                        resena.getCalificacion(),
                        resena.getComentario()
                ))
                .collect(Collectors.toList());
    }
}

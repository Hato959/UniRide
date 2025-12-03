package com.uniride.service;

import com.uniride.dto.response.UsuarioResponseDTO;
import com.uniride.exception.ResourceNotFoundException;
import com.uniride.model.Usuario;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FotoPerfilService {
    private final CloudinaryService cloudinaryService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public UsuarioResponseDTO subirYActualizarUsuario(Long usuarioId, MultipartFile file) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        try {
            String url = cloudinaryService.uploadFile(file, "uniride/perfiles");

            usuario.setFotoPerfilUrl(url);
            usuarioRepository.save(usuario);

            return usuarioService.obtenerPerfil(usuarioId);

        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen a Cloudinary: " + e.getMessage(), e);
        }
    }
}
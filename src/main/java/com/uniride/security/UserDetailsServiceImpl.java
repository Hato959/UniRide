package com.uniride.security;

import com.uniride.model.Usuario;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        GrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + usuario.getRolActivo().name());

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreoInstitucional(),
                usuario.getContrasena(),
                Collections.singleton(authority)
        );
    }
}

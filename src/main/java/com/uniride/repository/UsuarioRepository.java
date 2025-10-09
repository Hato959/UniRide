package com.uniride.repository;

import com.uniride.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    // Buscar usuario por correo institucional
    //@Query("SELECT u FROM Usuario u WHERE u.correoInstitucional = :correo")
    //Optional<Usuario> findByCorreo(@Param("correo") String correo);

    // Verificar si ya existe un correo registrado
    boolean existsByCorreoInstitucional(String correoInstitucional);

    // Verificar si ya existe un DNI registrado
    boolean existsByDni(String dni);
}

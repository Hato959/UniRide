package com.uniride.repository;

import com.uniride.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    // Buscar usuario por correo institucional
    //@Query("SELECT u FROM Usuario u WHERE u.correoInsitucional = :correo")
    //Optional<Usuario> findByCorreoInstitucional(@Param("correo") String correo);

    // Verificar si ya existe un correo registrado
    boolean existsByCorreoInsitucional(String correoInsitucional);

    // Verificar si ya existe un DNI registrado
    boolean existsByDni(String dni);
}

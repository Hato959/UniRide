package com.uniride.config;
import com.uniride.model.Usuario;
import com.uniride.model.enums.RolActivo;
import com.uniride.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner{
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@upc.edu.pe";

        if (usuarioRepository.findByCorreo(adminEmail).isEmpty()) {

            Usuario admin = Usuario.builder()
                    .nombre("Administrador General")
                    .correoInstitucional(adminEmail)
                    .contrasena(passwordEncoder.encode("admin123"))
                    .dni("00000000")
                    .verificado(true)
                    .rolActivo(RolActivo.ADMIN)
                    .build();

            usuarioRepository.save(admin);
            log.info("Usuario ADMIN creado: {} / admin123", adminEmail);
        } else {
            log.info("ℹUsuario ADMIN ya existente.");
        }
    }
}

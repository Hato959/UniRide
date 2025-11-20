package com.uniride.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Orígenes permitidos (puedes agregar tu frontend real si lo tienes desplegado)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",  // React
                "http://localhost:4200",  // Angular
                "http://127.0.0.1:3000",   // otra variante de localhost
                "https://uniride-fsxe.onrender.com"
        ));

        // Métodos permitidos
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Cabeceras permitidas
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Cache-Control",
                "Content-Type"
        ));

        // Permitir credenciales (para cookies o tokens)
        configuration.setAllowCredentials(true);

        // Cabeceras que el frontend puede leer (por ejemplo Authorization)
        configuration.setExposedHeaders(List.of("Authorization"));

        // Tiempo que el navegador puede cachear la configuración (en segundos)
        configuration.setMaxAge(3600L);

        // Aplica la configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

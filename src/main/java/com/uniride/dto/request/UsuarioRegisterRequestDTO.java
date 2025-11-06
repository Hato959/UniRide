package com.uniride.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UsuarioRegisterRequestDTO(
        @NotNull(message = "El nombre no puede ser nulo")
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El correo no puede estar vacío")
        @NotNull(message = "El correo no puede ser nulo")
        @Email(message = "El correo debe ser válido")
        String correoInstitucional,

        @NotBlank(message = "La contraseña es obligatoria")
        @NotNull(message = "La contraseña no puede ser nula")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasena,

        String carrera,
        String distrito,

        @NotNull(message = "El DNI no puede ser nulo")
        @NotBlank(message = "El DNI es obligatorio")
        @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
        String dni) {
}

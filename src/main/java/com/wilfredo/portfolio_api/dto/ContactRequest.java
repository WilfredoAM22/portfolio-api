package com.wilfredo.portfolio_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(
        @NotBlank(message = "El nombre es obligatorio.")
        @Size(min = 2, max = 85, message = "El nombre debe tener entre 2 y 85 caracteres.")
        String name,

        @NotBlank(message = "El correo electrónico es obligatorio.")
        @Email(message = "El formato del correo electrónico no es válido.")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres.")
        String email,

        @NotBlank(message = "El mensaje es obligatorio.")
        @Size(min = 10, max = 2000, message = "El mensaje debe tener entre 10 y 2000 caracteres.")
        String message
) {
}
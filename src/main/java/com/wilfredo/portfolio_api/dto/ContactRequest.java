package com.wilfredo.portfolio_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 85, message = "El nombre debe tener entre 2 y 85 caracteres")
        String name,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no tiene un formato válido")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
        String email,

        @NotBlank(message = "El asunto es obligatorio")
        @Size(min = 3, max = 120, message = "El asunto debe tener entre 3 y 120 caracteres")
        String subject,

        @NotBlank(message = "El mensaje es obligatorio")
        @Size(min = 10, max = 2000, message = "El mensaje debe tener entre 10 y 2000 caracteres")
        String message

) {
}

package com.idm.msvc.customer_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "El Username es obligatorio")
        String username,
        @NotBlank(message = "La Contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 8 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
                message = "La contraseña debe tener al menos una mayúscula, una minúscula y un número"
        )
        String password,
        String rol
) {
}

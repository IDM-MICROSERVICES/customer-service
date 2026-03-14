package com.idm.msvc.customer_service.dto.response;

import com.idm.msvc.customer_service.valueobject.Direccion;

public record CustomerRegisterResponse(
        Long id,
        String username,
        String nombre,
        String email,
        String apellidos,
        String documento,
        Direccion direccion
) {
}

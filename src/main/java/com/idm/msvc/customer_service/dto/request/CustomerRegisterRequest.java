package com.idm.msvc.customer_service.dto.request;

import com.idm.msvc.customer_service.valueobject.Direccion;

public record CustomerRegisterRequest(
        String username,
        String password,
        String nombre,
        String apellidos,
        String email,
        String telefono,
        String tipoDocumento,
        String documento,
        Direccion direccion
) {
}

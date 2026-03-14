package com.idm.msvc.customer_service.dto.response;

public record CustomerResponse (
        Long id,
        String documento,
        String nombre,
        String apellidos){
}

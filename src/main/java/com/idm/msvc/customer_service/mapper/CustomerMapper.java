package com.idm.msvc.customer_service.mapper;

import com.idm.msvc.customer_service.dto.response.CustomerResponse;
import com.idm.msvc.customer_service.entity.Customer;

public class CustomerMapper {

    public static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getDocumento(),
                customer.getNombre(),
                customer.getApellidos()
        );
    }

}

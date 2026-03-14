package com.idm.msvc.customer_service.service;


import com.idm.msvc.customer_service.dto.request.CustomerRegisterRequest;
import com.idm.msvc.customer_service.dto.response.CustomerRegisterResponse;
import com.idm.msvc.customer_service.dto.response.CustomerResponse;
import com.idm.msvc.customer_service.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerRegisterResponse registerCustomer(CustomerRegisterRequest request);
    Optional<Customer> findByDocumento(String documento);
    Customer getByDocumentoOrThrow(String documento);
    Customer getByIdOrThrow(Long id);
    List<CustomerResponse> getClients();
}

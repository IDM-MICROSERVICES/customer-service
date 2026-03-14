package com.idm.msvc.customer_service.controller;

import com.idm.msvc.customer_service.dto.request.CustomerRegisterRequest;
import com.idm.msvc.customer_service.dto.response.CustomerRegisterResponse;
import com.idm.msvc.customer_service.dto.response.CustomerResponse;
import com.idm.msvc.customer_service.entity.Customer;
import com.idm.msvc.customer_service.mapper.CustomerMapper;
import com.idm.msvc.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerRegisterResponse> registerCustomer(
            @RequestBody @Valid CustomerRegisterRequest request) {

        CustomerRegisterResponse response = service.registerCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/document/{documento}")
    public CustomerResponse getCustomerByDocumento(@PathVariable String documento) {
        Customer customer = service.getByDocumentoOrThrow(documento);
        return CustomerMapper.toResponse(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        try {
            Customer customer = service.getByIdOrThrow(id);
            return ResponseEntity.ok(CustomerMapper.toResponse(customer));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CustomerResponse>> getClients(){
        return ResponseEntity.ok(service.getClients());
    }

}

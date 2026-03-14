package com.idm.msvc.customer_service.repository;

import com.idm.msvc.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUsername(String username);
    boolean existsByDocumento(String documento);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Customer> findByDocumento(String documento);
}

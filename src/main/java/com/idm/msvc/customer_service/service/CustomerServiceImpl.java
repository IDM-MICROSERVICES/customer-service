package com.idm.msvc.customer_service.service;

import com.idm.msvc.customer_service.dto.request.CustomerRegisterRequest;
import com.idm.msvc.customer_service.dto.request.RegisterUserRequest;
import com.idm.msvc.customer_service.dto.response.CustomerRegisterResponse;
import com.idm.msvc.customer_service.dto.response.CustomerResponse;
import com.idm.msvc.customer_service.entity.Customer;
import com.idm.msvc.customer_service.exceptions.CustomerNotFoundException;
import com.idm.msvc.customer_service.exceptions.DocumentAlreadyExistsException;
import com.idm.msvc.customer_service.exceptions.EmailAlreadyExistsException;
import com.idm.msvc.customer_service.mapper.CustomerMapper;
import com.idm.msvc.customer_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.idm.msvc.customer_service.constants.Constantes.ACTIVO;
import static com.idm.msvc.customer_service.constants.Constantes.ROL;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;
    private final AuthClientService authClientService;


    public CustomerServiceImpl(CustomerRepository repository, AuthClientService authClientService) {
        this.repository = repository;
        this.authClientService = authClientService;
    }

    @Override
    public CustomerRegisterResponse registerCustomer(CustomerRegisterRequest request) {

        RegisterUserRequest registrarUsuarioRequest = new RegisterUserRequest(
                request.username(),
                request.password(),
                ROL
        );

        authClientService.registerUser(registrarUsuarioRequest);


        if (repository.existsByDocumento(request.documento())) {
            throw new DocumentAlreadyExistsException(
                    "El documento " + request.documento() + " ya está registrado"
            );
        }

        if (repository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(
                    "El email  " + request.email() + " ya está registrado"
            );
        }



        Customer customer = Customer.builder()
                .nombre(request.nombre())
                .apellidos(request.apellidos())
                .email(request.email())
                .telefono(request.telefono())
                .tipoDocumento(request.tipoDocumento())
                .documento(request.documento())
                .username(request.username())
                .direccion(request.direccion())
                .activo(ACTIVO)
                .build();

        Customer savedCustomer = repository.save(customer);

        return new CustomerRegisterResponse(
                savedCustomer.getId(),
                savedCustomer.getNombre(),
                savedCustomer.getApellidos(),
                savedCustomer.getUsername(),
                savedCustomer.getEmail(),
                savedCustomer.getDocumento(),
                savedCustomer.getDireccion()
        );

    }

    @Override
    public Optional<Customer> findByDocumento(String documento) {
        return repository.findByDocumento(documento);
    }

    public Customer getByDocumentoOrThrow(String documento) {
        return repository.findByDocumento(documento)
                .orElseThrow(() -> new CustomerNotFoundException("No existe el Cliente con el documento ingresado"));
    }

    @Override
    public Customer getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public List<CustomerResponse> getClients() {
        return repository.findAll().stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }
}

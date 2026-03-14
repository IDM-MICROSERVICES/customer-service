package com.idm.msvc.customer_service.service;

import com.idm.msvc.customer_service.dto.request.RegisterUserRequest;
import com.idm.msvc.customer_service.dto.response.RegisterUserResponse;
import com.idm.msvc.customer_service.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.idm.msvc.customer_service.constants.Constantes.AUTH_SERVICE_URL;

@Service
public class AuthClientService {

    private final RestTemplate restTemplate;


    public AuthClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        String url = AUTH_SERVICE_URL + "/register";

        HttpEntity<RegisterUserRequest> entity = new HttpEntity<>(request);

        try {
        ResponseEntity<RegisterUserResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                RegisterUserResponse.class
        );

        return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 409) {
                throw new UserAlreadyExistsException("El usuario "+request.username()+" ya existe");
            }
            throw e;
        }
    }

    public boolean validateToken(String token) {
        try {
            String url = AUTH_SERVICE_URL + "/validate-token";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Void.class
            );

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

}

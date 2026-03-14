package com.idm.msvc.customer_service.security;

import com.idm.msvc.customer_service.service.AuthClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final AuthClientService authClientService; // servicio que valida token
    private final RequestAttributeSecurityContextRepository repo = new RequestAttributeSecurityContextRepository();

    public JwtFilter(AuthClientService authClientService) {
        this.authClientService = authClientService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                log.info("Validando token en Customer-Service...");
                boolean valido = authClientService.validateToken(token);
                if (valido) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
                    authentication.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    repo.saveContext(context,request,response);
                    log.info("Autenticación establecida en el contexto para: " + token.substring(0, 10) + "...");
                }else {
                    log.info("AuthClientService dijo que el token NO es válido");
                }
            } catch (Exception e) {
                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}

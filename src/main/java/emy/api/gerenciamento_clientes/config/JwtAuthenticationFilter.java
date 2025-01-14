package emy.api.gerenciamento_clientes.config;

import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.service.ClienteService;
import emy.api.gerenciamento_clientes.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil util;
    private final ClienteService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (util.validarToken(token)) {
            String email = util.extrairEmail(token);
            Cliente cliente = service.buscarPorEmail(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(cliente, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}

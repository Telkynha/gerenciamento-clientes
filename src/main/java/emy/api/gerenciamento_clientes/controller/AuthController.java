package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.ClienteRequest;
import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.service.ClienteService;
import emy.api.gerenciamento_clientes.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteService service;
    private final JwtUtil util;

    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody @Valid ClienteRequest request) {
        Cliente cliente = service.autenticar(request.getEmail(), request.getSenha());
        String token = util.gerarToken(cliente.getEmail());
        return ResponseEntity.ok(token);
    }

}

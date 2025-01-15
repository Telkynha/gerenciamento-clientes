package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.ClienteRequest;
import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.service.ClienteService;
import emy.api.gerenciamento_clientes.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Autenticação")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteService service;
    private final JwtUtil util;

    @ApiOperation(value = "Autenticar o cliente e gerar token JWT", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token gerado com sucesso"),
            @ApiResponse(code = 400, message = "Falha na autenticação")
    })
    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody @Valid ClienteRequest request) {
        Cliente cliente = service.autenticar(request.getEmail(), request.getSenha());
        String token = util.gerarToken(cliente.getEmail());
        return ResponseEntity.ok(token);
    }
}

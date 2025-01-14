package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.ClienteRequest;
import emy.api.gerenciamento_clientes.dto.ClienteResponse;
import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.mapper.ClienteMapper;
import emy.api.gerenciamento_clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponse> salvarCliente(@RequestBody @Valid ClienteRequest request) {
        Cliente cliente = ClienteMapper.toEntity(request);
        Cliente clienteSalvo = service.salvarCliente(cliente);
        ClienteResponse response = ClienteMapper.toResponse(clienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = service.buscarPorId(id);
        return ResponseEntity.ok(ClienteMapper.toResponse(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        service.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

}

package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.ContaRequest;
import emy.api.gerenciamento_clientes.dto.ContaResponse;
import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.mapper.ContaMapper;
import emy.api.gerenciamento_clientes.service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Contas")
@RestController
@RequestMapping("api/conta")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService service;

    @ApiOperation(value = "Buscar conta por ID", response = ContaResponse.class)
    @GetMapping("/{id}")
    public ResponseEntity<ContaResponse> buscarContaPorId(@PathVariable Long id) {
        Conta conta = service.buscarContaPorId(id);
        ContaResponse response = ContaMapper.toResponse(conta);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Atualizar saldo da conta", response = Void.class)
    @PatchMapping("/{id}/saldo")
    public ResponseEntity<Void> atualizarSaldo(@PathVariable Long id, @RequestBody @Valid ContaRequest request) {
        Conta conta = service.buscarContaPorId(id);
        service.atualizarSaldo(conta, request.getSaldo());
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Imprimir saldo da conta", response = String.class)
    @GetMapping("/{id}/saldo")
    public ResponseEntity<String> imprimirSaldo(@PathVariable Long id) {
        String saldo = service.imprimirSaldo(id);
        return ResponseEntity.ok(saldo);
    }
}

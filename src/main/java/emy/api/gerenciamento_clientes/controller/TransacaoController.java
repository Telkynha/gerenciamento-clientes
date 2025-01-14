package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.TransacaoDTO;
import emy.api.gerenciamento_clientes.entity.Transacao;
import emy.api.gerenciamento_clientes.mapper.TransacaoMapper;
import emy.api.gerenciamento_clientes.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService service;

    @PostMapping
    public ResponseEntity<TransacaoDTO> criarTransacao(@RequestBody @Valid TransacaoDTO dto) {
        Transacao transacao = TransacaoMapper.toEntity(dto);
        Transacao transacaoSalva = service.adicionarTransacao(transacao);
        TransacaoDTO response = TransacaoMapper.toResponse(transacaoSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransacaoDTO> editarTransacao(@PathVariable Long id, @RequestBody @Valid TransacaoDTO dto) {
        Transacao transacao = service.editarTransacao(id, dto);
        return ResponseEntity.ok(TransacaoMapper.toResponse(transacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTranscao(@PathVariable Long id) {
        service.deletarTransacao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> buscarTransacaoPorId(@PathVariable Long id) {
        Transacao transacao = service.buscarPorId(id);
        TransacaoDTO response = TransacaoMapper.toResponse(transacao);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<String> listarTransacoes(
            @RequestParam Long contaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {

        if (dataFinal.isBefore(dataInicial)) {
            return ResponseEntity.badRequest().body(null);
        }

        String historico = service.listarTransacoes(contaId, dataInicial, dataFinal);
        return ResponseEntity.ok(historico);
    }


}

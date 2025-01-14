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
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> buscarTransacaoPorId(@PathVariable Long id) {
        Transacao transacao = service.buscarPorId(id);
        TransacaoDTO response = TransacaoMapper.toResponse(transacao);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTranscao(@PathVariable Long id) {
        service.deletarTransacao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> listarTransacoes(
            @RequestParam Long contaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {

        List<Transacao> historico = service.buscarPorPeriodo(contaId, dataInicial, dataFinal);
        List<TransacaoDTO> response = historico.stream()
                .map(TransacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}

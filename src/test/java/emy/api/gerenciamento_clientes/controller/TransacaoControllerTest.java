package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.TransacaoDTO;
import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.entity.Transacao;
import emy.api.gerenciamento_clientes.service.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TransacaoControllerTest {
    @Mock
    TransacaoService service;

    @InjectMocks
    TransacaoController transacaoController;

    @Mock
    TransacaoDTO transacaoDTO;

    @Mock
    Transacao transacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transacao = new Transacao();
        transacao.setId(1L);
        transacao.setValor(new BigDecimal(100));
        transacao.setConta(new Conta());
    }

    @Test
    void testCriarTransacao() {
        when(service.adicionarTransacao(any(Transacao.class))).thenReturn(transacao);

        ResponseEntity<TransacaoDTO> result = transacaoController.criarTransacao(transacaoDTO);
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testEditarTransacao() {
        when(service.editarTransacao(anyLong(), any(TransacaoDTO.class))).thenReturn(transacao);

        ResponseEntity<TransacaoDTO> result = transacaoController.editarTransacao(1L, transacaoDTO);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeletarTransacao() {
        ResponseEntity<Void> result = transacaoController.deletarTranscao(1L);
        verify(service).deletarTransacao(anyLong());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void testBuscarTransacaoPorId() {
        when(service.buscarPorId(anyLong())).thenReturn(transacao);

        ResponseEntity<TransacaoDTO> result = transacaoController.buscarTransacaoPorId(1L);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testListarTransacoes() {
        when(service.listarTransacoes(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn("listarTransacoesResponse");

        ResponseEntity<String> result = transacaoController.listarTransacoes(1L, LocalDateTime.now(), LocalDateTime.now());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

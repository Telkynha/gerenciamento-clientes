package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.dto.TransacaoDTO;
import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.entity.TipoTransacao;
import emy.api.gerenciamento_clientes.entity.Transacao;
import emy.api.gerenciamento_clientes.repository.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TransacaoServiceTest {
    @Mock
    TransacaoRepository repository;

    @Mock
    ContaService contaService;

    @InjectMocks
    TransacaoService transacaoService;

    @Mock
    Transacao transacao;

    @Mock
    Conta conta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conta = new Conta();
        transacao = new Transacao();
        conta.setId(1L);
        conta.setSaldo(new BigDecimal(1000));
        transacao.setConta(conta);
        transacao.setValor(new BigDecimal(100));
        transacao.setTipo(TipoTransacao.RECEITA);
    }

    @Test
    void testAdicionarTransacao() {
        when(repository.save(any(Transacao.class))).thenReturn(transacao);
        when(contaService.buscarContaPorId(1L)).thenReturn(conta);
        Transacao result = transacaoService.adicionarTransacao(transacao);

        contaService.atualizarSaldo(conta, transacao.getValor());
        Assertions.assertEquals(transacao.getValor(), result.getValor());
    }

    @Test
    void testEditarTransacao() {
        when(repository.save(any(Transacao.class))).thenReturn(transacao);
        when(repository.findById(anyLong())).thenReturn(Optional.of(transacao));

        TransacaoDTO transacaoDTO = new TransacaoDTO(1L,new BigDecimal(100), "descricao", LocalDateTime.now(), TipoTransacao.RECEITA);
        Transacao result = transacaoService.editarTransacao(1L, transacaoDTO);

        verify(contaService).atualizarSaldo(conta, transacaoDTO.getValor());
        Assertions.assertEquals(transacao, result);
    }

    @Test
    void testDeletarTransacao() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(transacao));

        transacaoService.deletarTransacao(1L);
        verify(repository).deleteById(1L);
        verify(contaService).atualizarSaldo(conta, transacao.getValor().negate());
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(transacao));

        Transacao result = transacaoService.buscarPorId(1L);
        Assertions.assertEquals(transacao, result);
    }

    @Test
    void testBuscarPorPeriodo() {
        when(repository.findByContaIdAndDataHoraBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(transacao));

        List<Transacao> result = transacaoService.buscarPorPeriodo(1L, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        Assertions.assertEquals(List.of(transacao), result);
    }

    @Test
    void testListarTransacoes() {
        when(repository.findByContaIdAndDataHoraBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(transacao));

        String result = transacaoService.listarTransacoes(1L, LocalDateTime.now().minusDays(1), LocalDateTime.now());

    }
}

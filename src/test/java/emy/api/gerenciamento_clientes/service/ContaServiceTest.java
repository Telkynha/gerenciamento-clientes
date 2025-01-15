package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.repository.ContaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ContaServiceTest {
    @Mock
    ContaRepository repository;

    @InjectMocks
    ContaService contaService;

    @Mock
    Conta conta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conta = new Conta();
        conta.setSaldo(new BigDecimal(1000));
    }

    @Test
    void testBuscarContaPorId() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(conta));

        Conta result = contaService.buscarContaPorId(1L);
        Assertions.assertEquals(conta, result);
    }

    @Test
    void testAtualizarSaldo() {
        when(repository.save(any(Conta.class))).thenReturn(conta);

        BigDecimal novoSaldo = conta.getSaldo().add(new BigDecimal(100));
        contaService.atualizarSaldo(conta, new BigDecimal(100));

        verify(repository).save(conta);
        Assertions.assertEquals(novoSaldo, conta.getSaldo());
    }

    @Test
    void testImprimirSaldo() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(conta));

        String result = contaService.imprimirSaldo(1L);
        Assertions.assertEquals("Saldo da conta: R$ 1000,00", result);
    }
}

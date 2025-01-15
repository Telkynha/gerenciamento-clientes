package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.ContaRequest;
import emy.api.gerenciamento_clientes.dto.ContaResponse;
import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.service.ContaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class ContaControllerTest {
    @Mock
    ContaService service;

    @InjectMocks
    ContaController contaController;

    @Mock
    Conta conta;

    @Mock
    ContaRequest contaRequest;

    @Mock
    ContaResponse contaResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarContaPorId() {
        when(service.buscarContaPorId(anyLong())).thenReturn(conta);
        when(conta.getId()).thenReturn(1L);

        ResponseEntity<ContaResponse> result = contaController.buscarContaPorId(1L);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    void testAtualizarSaldo() {
        when(service.buscarContaPorId(anyLong())).thenReturn(conta);

        ResponseEntity<Void> result = contaController.atualizarSaldo(1L, new ContaRequest(new BigDecimal(0)));
        verify(service).atualizarSaldo(any(Conta.class), any(BigDecimal.class));
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void testImprimirSaldo() {
        when(service.imprimirSaldo(anyLong())).thenReturn("imprimirSaldoResponse");

        ResponseEntity<String> result = contaController.imprimirSaldo(1L);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

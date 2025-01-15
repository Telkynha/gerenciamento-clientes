package emy.api.gerenciamento_clientes.controller;

import emy.api.gerenciamento_clientes.dto.ClienteRequest;
import emy.api.gerenciamento_clientes.dto.ClienteResponse;
import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

class ClienteControllerTest {
    @Mock
    ClienteService service;

    @InjectMocks
    ClienteController clienteController;

    @Mock
    ClienteRequest clienteRequest;

    @Mock
    ClienteResponse clienteResponse;

    @Mock
    Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
    }

    @Test
    void testSalvarCliente() {
        when(service.salvarCliente(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<ClienteResponse> result = clienteController.salvarCliente(clienteRequest);
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testBuscarClientePorId() {
        when(service.buscarPorId(anyLong())).thenReturn(cliente);

        ResponseEntity<ClienteResponse> result = clienteController.buscarClientePorId(1L);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDeletarCliente() {
        ResponseEntity<Void> result = clienteController.deletarCliente(1L);
        verify(service).deletarCliente(anyLong());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}

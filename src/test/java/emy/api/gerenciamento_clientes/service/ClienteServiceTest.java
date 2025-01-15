package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.exception.ClienteNotFoundException;
import emy.api.gerenciamento_clientes.exception.LoginFailedException;
import emy.api.gerenciamento_clientes.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ClienteServiceTest {
    @Mock
    ClienteRepository repository;

    @Mock
    BCryptPasswordEncoder encoder;

    @InjectMocks
    ClienteService clienteService;

    @Mock
    Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setSenha("senha");
        when(encoder.encode(any(CharSequence.class))).thenReturn("senhacriptografada");
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.salvarCliente(cliente);

        Assertions.assertEquals(cliente, result);
        Assertions.assertEquals("senhacriptografada", result.getSenha());
        verify(encoder).encode("senha");
        verify(repository).save(cliente);
    }

    @Test
    void testDeletarCliente() {
        when(repository.existsById(1L)).thenReturn(true);

        clienteService.deletarCliente(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testDeletarClienteNaoEncontrado() {
        when(repository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(ClienteNotFoundException.class, () -> clienteService.deletarCliente(1L));
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.buscarPorId(1L);

        Assertions.assertEquals(cliente, result);
    }

    @Test
    void testBuscarPorIdClienteNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class, () -> clienteService.buscarPorId(1L));
    }

    @Test
    void testBuscarPorEmail() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.buscarPorEmail("email");

        Assertions.assertEquals(cliente, result);
    }

    @Test
    void testBuscarPorEmailClienteNaoEncontrado() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class, () -> clienteService.buscarPorEmail("email"));
    }

    @Test
    void testAutenticar() {
        Cliente cliente = new Cliente();
        cliente.setEmail("email");
        cliente.setSenha("senhacriptografada");
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(cliente));
        when(encoder.matches(any(CharSequence.class), anyString())).thenReturn(true);

        Cliente result = clienteService.autenticar("email", "senha");

        Assertions.assertEquals(cliente, result);
        verify(repository).findByEmail("email");
        verify(encoder).matches("senha", "senhacriptografada");
    }

    @Test
    void testAutenticarSenhaIncorreta() {
        Cliente cliente = new Cliente();
        cliente.setEmail("email");
        cliente.setSenha("senhacriptografada");
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(cliente));
        when(encoder.matches(any(CharSequence.class), anyString())).thenReturn(false);

        Assertions.assertThrows(LoginFailedException.class, () -> clienteService.autenticar("email", "senhaErrada"));
    }

    @Test
    void testAutenticarClienteNaoEncontrado() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class, () -> clienteService.autenticar("email", "senha"));
    }
}

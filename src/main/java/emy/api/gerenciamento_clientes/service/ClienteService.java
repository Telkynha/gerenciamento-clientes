package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.entity.Conta;
import emy.api.gerenciamento_clientes.exception.ClienteNotFoundException;
import emy.api.gerenciamento_clientes.exception.LoginFailedException;
import emy.api.gerenciamento_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder encoder;

    public Cliente salvarCliente(Cliente cliente) {
        Conta novaConta = new Conta();
        novaConta.setSaldo(BigDecimal.ZERO);
        cliente.setConta(novaConta);

        cliente.setSenha(encoder.encode(cliente.getSenha()));
        return repository.save(cliente);
    }

    public void deletarCliente(Long id) {
        if (!repository.existsById(id)) {
            throw new ClienteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    public Optional<Cliente> findByEmail(String email) {
        return repository.findByEmail(email);
    }


    public Cliente buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ClienteNotFoundException("Nenhum cliente foi encontrado com email: " + email));
    }

    public Cliente autenticar(String email, String senha) {
        Cliente cliente = repository.findByEmail(email)
                .orElseThrow(ClienteNotFoundException::new);

        if (!encoder.matches(senha, cliente.getSenha())) {
            throw new LoginFailedException();
        }

        return cliente;
    }
}

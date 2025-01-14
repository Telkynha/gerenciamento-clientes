package emy.api.gerenciamento_clientes.service;

import emy.api.gerenciamento_clientes.entity.Cliente;
import emy.api.gerenciamento_clientes.exception.ClienteNotFoundException;
import emy.api.gerenciamento_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final BCryptPasswordEncoder encoder;

    public Cliente salvarCliente(Cliente cliente) {
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

    public Cliente buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ClienteNotFoundException("Nenhum cliente foi encontrado com email: " + email));
    }
}

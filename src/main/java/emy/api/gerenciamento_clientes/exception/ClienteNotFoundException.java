package emy.api.gerenciamento_clientes.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(String message) {
        super(message);
    }

    public ClienteNotFoundException() {
        super("Cliente não encontrado");
    }

    public ClienteNotFoundException(Long id) {
        super("Nenhum cliente foi encontrado com ID: " + id);
    }
}

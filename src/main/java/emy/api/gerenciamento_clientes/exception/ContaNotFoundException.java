package emy.api.gerenciamento_clientes.exception;

public class ContaNotFoundException extends RuntimeException {

    public ContaNotFoundException(String message) {
        super(message);
    }

    public ContaNotFoundException() {
        super("Conta não encontrada");
    }

    public ContaNotFoundException(Long id) {
        super("Nenhuma conta foi encontrada com ID: " + id);
    }
}

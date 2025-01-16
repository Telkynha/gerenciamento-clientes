package emy.api.gerenciamento_clientes.exception;

public class TransacaoNotFoundException extends RuntimeException {

    public TransacaoNotFoundException(String message) {
        super(message);
    }

    public TransacaoNotFoundException() {
        super("Transação não encontrada");
    }
}

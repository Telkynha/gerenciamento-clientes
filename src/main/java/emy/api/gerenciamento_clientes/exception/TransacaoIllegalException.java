package emy.api.gerenciamento_clientes.exception;

public class TransacaoIllegalException extends RuntimeException {

    public TransacaoIllegalException(String message) {
        super(message);
    }

    public TransacaoIllegalException() {
        super("Transação não permitida");
    }
}

package emy.api.gerenciamento_clientes.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a despesa");
    }
}

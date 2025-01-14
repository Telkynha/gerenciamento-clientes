package emy.api.gerenciamento_clientes.exception;

public class DataIllegalException extends RuntimeException {
    public DataIllegalException(String message) {
        super(message);
    }

    public DataIllegalException() {
        super("Data de inicio não pode ser antes da data final!");
    }
}

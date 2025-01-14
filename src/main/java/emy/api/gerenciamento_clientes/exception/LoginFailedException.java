package emy.api.gerenciamento_clientes.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
    public LoginFailedException() {
        super("Credenciais invalidas!");
    }
}

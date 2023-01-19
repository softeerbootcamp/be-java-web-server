package exception;

public class NonLogInException extends RuntimeException {
    public NonLogInException(String message) {
        super(message);
    }
}

package exception;

public class UserValidationException extends RuntimeException {

    public UserValidationException() {
    }

    public UserValidationException(String message) {
        super(message);
    }
}

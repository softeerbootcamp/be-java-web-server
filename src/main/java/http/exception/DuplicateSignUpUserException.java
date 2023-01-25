package http.exception;

public class DuplicateSignUpUserException extends RuntimeException {
    public DuplicateSignUpUserException(String message) {
        super(message);
    }
}

package service.user.exception;

public class LoginIdDuplicatedException extends RuntimeException {

    public LoginIdDuplicatedException(String message) {
        super(message);
    }
}

package service.user.exception;

public class LoginIdNotExistException extends RuntimeException {

    public LoginIdNotExistException(String message) {
        super(message);
    }
}

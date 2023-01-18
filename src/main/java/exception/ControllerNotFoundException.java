package exception;

public class ControllerNotFoundException extends RuntimeException {

    ControllerNotFoundException() {

    }

    public ControllerNotFoundException(String message) {
        super(message);
    }
}

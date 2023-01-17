package exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException() {
    }

    public UrlNotFoundException(String message) {
        super(message);
    }
}

package exception;

public class HttpNotFoundException extends RuntimeException {

    public HttpNotFoundException() {
    }

    public HttpNotFoundException(Throwable cause) {
        super(cause);
    }
}

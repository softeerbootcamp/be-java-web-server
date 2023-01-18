package exception;

public class ConnectionClosedException extends RuntimeException {
    public ConnectionClosedException(Throwable cause) {
        super(cause);
    }
}

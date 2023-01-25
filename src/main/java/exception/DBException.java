package exception;

public class DBException extends RuntimeException {
    public DBException(Throwable cause) {
        super(cause);
    }
}

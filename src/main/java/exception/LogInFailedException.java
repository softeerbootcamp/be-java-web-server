package exception;

public class LogInFailedException extends IllegalArgumentException {
    public LogInFailedException(String message) {
        super(message);
    }
}

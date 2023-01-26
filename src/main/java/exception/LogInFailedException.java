package exception;

public class LogInFailedException extends Exception {

    public LogInFailedException(){
    }
    public LogInFailedException(String message) {
        super(message);
    }
}

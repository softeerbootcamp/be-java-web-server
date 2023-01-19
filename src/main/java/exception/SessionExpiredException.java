package exception;

public class SessionExpiredException extends WasException{
    private static final String MESSAGE = "세션이 만료되었습니다.";
    public SessionExpiredException() {
        super(MESSAGE);
    }
}

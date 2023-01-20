package exception;

public class SessionNotFoundException extends WasException {
    private static final String MESSAGE = "해당 세션을 찾을 수 없습니다.";

    public SessionNotFoundException() {
        super(MESSAGE);
    }
}

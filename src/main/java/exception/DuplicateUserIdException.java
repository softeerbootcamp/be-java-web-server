package exception;

public class DuplicateUserIdException extends WasException {

    private static final String MESSAGE = "이미 존재하는 유저 아이디 입니다.";

    public DuplicateUserIdException() {
        super(MESSAGE);
    }
}

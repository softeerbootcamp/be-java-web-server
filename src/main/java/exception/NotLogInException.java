package exception;

public class NotLogInException extends Exception{
    public NotLogInException() {
        super("로그인 되어 있지 않습니다");
    }
}

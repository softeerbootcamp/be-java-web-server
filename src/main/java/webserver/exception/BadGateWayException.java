package webserver.exception;

public class BadGateWayException extends RuntimeException{

    private static final String msg = "중간 계층 오류";

    public BadGateWayException() {
        super(msg);
    }
}

package webserver.exception;

public class BadGateWayException extends HttpRequestError{

    private static final String msg = "중간 계층 오류";
    private static final String errorCode = "500";

    public BadGateWayException() {
        super(msg, errorCode);
    }

}

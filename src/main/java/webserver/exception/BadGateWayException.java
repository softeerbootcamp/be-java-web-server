package webserver.exception;

public class BadGateWayException extends HttpRequestError{

    private static final String msg = "BAD GATEWAY";
    private static final String errorCode = "500";

    public BadGateWayException() {
        super(msg, errorCode);
    }

}

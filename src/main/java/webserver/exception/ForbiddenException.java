package webserver.exception;

public class ForbiddenException extends HttpRequestError{
    private static final String msg = "FORBIDDEN";
    private static final String errorCode = "403";

    public ForbiddenException() {
        super(msg, errorCode);
    }

}

package webserver.exception;

public class ForbiddenException extends HttpRequestError{
    private static final String msg = "권한이 존재하지 않습니다";
    private static final String errorCode = "403";

    public ForbiddenException() {
        super(msg, errorCode);
    }

}

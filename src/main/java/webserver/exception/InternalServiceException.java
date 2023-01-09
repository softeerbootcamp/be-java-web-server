package webserver.exception;

public class InternalServiceException extends HttpRequestError{

    private static final String msg = "서버 내부 오류";
    private static final String errorCode = "500";


    public InternalServiceException() {
        super(msg, errorCode);
    }

}


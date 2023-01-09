package webserver.exception;

public class InternalServiceException extends HttpRequestError{

    private static final String msg = "INTERNAL SERVER ERROR";
    private static final String errorCode = "500";


    public InternalServiceException() {
        super(msg, errorCode);
    }

}


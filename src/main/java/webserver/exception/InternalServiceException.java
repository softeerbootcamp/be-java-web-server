package webserver.exception;

public class InternalServiceException extends RuntimeException{

    private static final String msg = "서버 내부 오류";

    public InternalServiceException() {
        super(msg);
    }
}


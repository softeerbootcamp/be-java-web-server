package webserver.exception;

public class ServiceUnavailableExcpetion extends HttpRequestError{

    private static final String msg = "서비스 제공 불가";
    private static final String errorCode = "503";

    public ServiceUnavailableExcpetion() {
        super(msg, errorCode);
    }

}

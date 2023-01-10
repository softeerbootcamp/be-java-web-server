package webserver.exception;

public class ServiceUnavailableExcpetion extends HttpRequestError{

    private static final String msg = "SERVICE_UNAVAILABLE";
    private static final String errorCode = "503";

    public ServiceUnavailableExcpetion() {
        super(msg, errorCode);
    }

}

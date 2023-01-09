package webserver.exception;

public class ServiceUnavailableExcpetion extends RuntimeException{

    private static final String msg = "서비스 제공 불가";

    public ServiceUnavailableExcpetion() {
        super(msg);
    }
}

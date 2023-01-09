package webserver.exception;

public class ForbiddenException extends RuntimeException{
    private static final String msg = "권한이 존재하지 않습니다";

    public ForbiddenException() {
        super(msg);
    }
}

package webserver.exception;

public class NoFileException extends HttpRequestError{

    private static final String msg = "요청하신 파일이 존재하지 않습니다";
    private static final String errorCode = "404";

    public NoFileException() {
        super(msg, errorCode);
    }

}

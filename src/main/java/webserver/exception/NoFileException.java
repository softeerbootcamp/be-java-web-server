package webserver.exception;

public class NoFileException extends HttpRequestError{

    private static final String msg = "NOT FOUND";
    private static final String errorCode = "404";

    public NoFileException() {
        super(msg, errorCode);
    }

}

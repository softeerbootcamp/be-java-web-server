package webserver.exception;

public class NoFileException extends RuntimeException{

    private static final String msg = "요청하신 파일이 존재하지 않습니다";

    public NoFileException() {
        super(msg);
    }
}

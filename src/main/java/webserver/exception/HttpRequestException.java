package webserver.exception;

public class HttpRequestException extends RuntimeException{

    private static String msg;
    private static String errorCode;

    public HttpRequestException(String msg, String errorCode) {
        super(msg);
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }

    public String getErrorMsg(){
        return msg;
    }

}

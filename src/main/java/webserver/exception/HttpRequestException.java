package webserver.exception;

import webserver.domain.StatusCodes;

public class HttpRequestException extends RuntimeException{

    private static StatusCodes statusCode;
    private static String msg;
    
    public HttpRequestException(StatusCodes statusCode) {
        super(statusCode.getStatusMsg());
        this.statusCode = statusCode;
    }

    public HttpRequestException(StatusCodes statusCode, String msg) {
        super(statusCode.getStatusMsg());
        this.statusCode = statusCode;
        this.msg = msg;

    }

    public StatusCodes getErrorCode(){
        return statusCode;
    }

    public String getMsg() {return msg;}

}

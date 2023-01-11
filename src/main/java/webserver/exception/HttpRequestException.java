package webserver.exception;

import webserver.domain.StatusCodes;

public class HttpRequestException extends RuntimeException{

    private static StatusCodes statusCode;

    public HttpRequestException(StatusCodes statusCode) {
        super(statusCode.getStatusMsg());
        this.statusCode = statusCode;
    }

    public StatusCodes getErrorCode(){
        return statusCode;
    }

}

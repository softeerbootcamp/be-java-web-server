package webserver.exception;

import webserver.domain.StatusCodes;

public class HttpRequestException extends Exception{

    private StatusCodes statusCode;
    private String msg;
    private String redirectURL;

    
    public HttpRequestException(StatusCodes statusCode) {
        super(statusCode.getStatusMsg());
        this.statusCode = statusCode;
    }

    public HttpRequestException(StatusCodes statusCode, String msg) {
        super(statusCode.getStatusMsg());
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public HttpRequestException(StatusCodes statusCode, String msg, String redirectURL) {
        super(statusCode.getStatusMsg());
        this.statusCode = statusCode;
        this.msg = msg;
        this.redirectURL = redirectURL;
    }

    public StatusCodes getErrorCode(){
        return statusCode;
    }

    public String getMsg() {return msg;}

    public String getRedirectURL() {return redirectURL;}
}

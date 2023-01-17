package webserver.exception;

import webserver.domain.StatusCodes;

public class HttpRequestException extends RuntimeException{
    //TODO : 해당 클래스의 상위 Exception을 RuntieException이 아닌 Excpetion으로 변경

    private static StatusCodes statusCode;
    private static String msg;
    private static String redirectURL;

    
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

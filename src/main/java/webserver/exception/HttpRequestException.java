package webserver.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import webserver.domain.StatusCodes;

@Builder
@AllArgsConstructor
public class HttpRequestException extends RuntimeException{

    private StatusCodes statusCode;
    private String msg;
    private String redirectURL;

    public StatusCodes getErrorCode(){
        return statusCode;
    }

    public String getMsg() {return msg;}

    public String getRedirectURL() {return redirectURL;}
}

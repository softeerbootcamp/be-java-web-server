package webserver.domain;

import java.util.Arrays;

public enum StatusCodes {

    OK("200", "OK"),
    SEE_OTHER("302", "SEE_OTHER"),
    BAD_REQUEST("400", "BAD_REQUEST"),
    NOT_FOUND("404", "NOT_FOUND"),
    FORBIDDEN("403", "FORBIDDEN"),
    INTERNAL_SERVER_ERROR("500", "INTERNAL_SERVER_ERROR"),
    BAD_GATEWAY("502", "BAD_GATEWAY"),
    SERVICE_UNAVAILABLE("503", "SERVICE_UNAVAILABLE");

    private String statusCode;
    private String statusMsg;
    private StatusCodes(String statusCode, String statusMsg){
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public String getStatusCode(){
        return statusCode;
    }

    public String getStatusMsg(){
        return statusMsg;
    }

    public static StatusCodes findStatus(String msg){
        return Arrays.stream(StatusCodes.values())
                .filter(status -> status.getStatusMsg().equals(msg))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 에러가 존재하지 않습니다"));
    }

}

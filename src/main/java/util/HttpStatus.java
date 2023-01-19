package util;

import response.HttpResponse;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    RE_DIRECT(302, "Found"),

    UN_AUTHORIZED(401, "UnAuthorized"),
    NOT_FOUND(404,"NOT_FOUND"),

    FORBIDDEN(403, "Forbidden"),

    INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR")
    ;

    private int code;
    private String message;
    HttpStatus(int code, String message) {
        this.code=code;
        this.message = message;
    }



    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

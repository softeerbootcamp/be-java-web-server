package util;

import response.HttpResponse;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    RE_DIRECT(302, "Found"),
    NOT_FOUND(404,"NOT_FOUND"),

    METHOD_NOT_ALLOWED(405, "Method Not Allowd"),

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

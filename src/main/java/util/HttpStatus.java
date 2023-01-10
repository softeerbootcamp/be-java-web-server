package util;

import response.HttpResponse;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    NOT_FOUND(404,"NOT_FOUND")
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

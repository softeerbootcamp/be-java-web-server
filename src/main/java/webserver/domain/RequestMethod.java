package webserver.domain;

import webserver.exception.HttpRequestException;

import java.util.Arrays;

public enum RequestMethod {

    GET("GET"),
    HEAD("HEAD"),

    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    TRACE("TRACE"),
    DELETE("DELETE");

    public String type;
    private RequestMethod(String type){
        this.type = type;
    }

    public static RequestMethod findType(String givenStr){
        return Arrays.stream(RequestMethod.values())
                .filter(method-> method.type.equals(givenStr))
                .findFirst()
                .orElseThrow(()->new HttpRequestException(StatusCodes.METHOD_NOT_ALLOWED));
    }

}

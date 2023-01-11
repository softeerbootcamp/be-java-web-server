package util;

import request.HttpRequest;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    NOT_MATCH("NOT_MATCH"),
    ;

    public static HttpMethod findMethod(HttpRequest httpRequest) {
        String headerFirstLine = httpRequest.getHeaderContents().get(HttpRequest.REQUEST_LINE);
        return HttpMethod.getMethod(headerFirstLine.split(" ")[0]);
    }
    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod getMethod(String method) {
        return Arrays.stream(values())
                .filter(value -> value.isMatch(method))
                .findAny()
                .orElse(NOT_MATCH);
    }


    private boolean isMatch(String method) {
        return this.method.equals(method);
    }
}

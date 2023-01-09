package util.reader;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    NOT_MATCH("NOT_MATCH"),
    ;

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

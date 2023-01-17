package http.common;

import java.util.Arrays;

public enum Method {
    GET("GET"),
    POST("POST");

    private String value;

    Method(String value) {
        this.value = value;
    }

    public static Method find(String method) {
        return Arrays.stream(Method.values())
                .filter(m -> m.value.equals(method))
                .findAny()
                .orElse(GET);
    }
}

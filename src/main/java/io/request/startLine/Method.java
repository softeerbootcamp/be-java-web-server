package io.request.startLine;

import java.util.Arrays;

public enum Method {
    GET("GET");

    private String value;

    Method(String value) {
        this.value = value;
    }

    public Method find(String method) {
        return Arrays.stream(Method.values())
                .filter(m -> m.value.equals(value))
                .findAny()
                .orElse(GET);
    }
}

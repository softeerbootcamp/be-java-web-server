package io.request.startLine;

import java.util.Arrays;

public enum Protocol {
    HTTP_1_1("HTTP/1.1");

    private String value;

    Protocol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Protocol find(String value) {
        return Arrays.stream(Protocol.values())
                .filter(p -> p.value.equals(value))
                .findAny()
                .orElse(HTTP_1_1);
    }
}

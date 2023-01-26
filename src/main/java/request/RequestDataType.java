package request;

import java.util.Arrays;

public enum RequestDataType {
    QUERY_STRING(".*\\?(.*)"),
    PATH_VARIABLE("^.*/([0-9]+)$"),
    FILE(".*\\.(html|ico|css|js|tff|woff)$"),
    IN_BODY(""),

    ;


    private String regex;

    RequestDataType(String regex) {
        this.regex = regex;
    }

    public static RequestDataType getUrlType(String url) {
        return Arrays.stream(values())
                .filter(value -> url.matches(value.regex))
                .findAny()
                .orElse(IN_BODY);
    }


}

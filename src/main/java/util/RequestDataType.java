package util;

import java.util.Arrays;

public enum RequestDataType {
    QUERY_STRING(".*\\?(.*)"),
    TEMPLATES_FILE(".*\\.(html|ico)$"),
    STATIC_FILE(".*\\.(css|js|tff|woff)$"),
    IN_BODY(""),

    ;


    private String regex;

    RequestDataType(String regex) {
        this.regex = regex;
    }

    public static RequestDataType getUrlType(String url){
        return Arrays.stream(values())
                .filter(value -> url.matches(value.regex))
                .findAny()
                .orElse(IN_BODY);
    }


}

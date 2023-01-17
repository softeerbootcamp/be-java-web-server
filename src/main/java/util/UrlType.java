package util;

import util.error.HttpsErrorMessage;

import java.net.ProtocolException;
import java.util.Arrays;
import java.util.Optional;

public enum UrlType {
    QUERY_STRING(".*\\?(.*)"),
    TEMPLATES_FILE(".*\\.(html|ico)$"),
    STATIC_FILE(".*\\.(css|js|tff|woff)$"),
    NOTHING(""),
    ;


    private String regex;

    UrlType(String regex) {
        this.regex = regex;
    }

    public static UrlType getUrlType(String url){
        return Arrays.stream(values())
                .filter(value -> url.matches(value.regex))
                .findAny()
                .orElse(NOTHING);
    }

}

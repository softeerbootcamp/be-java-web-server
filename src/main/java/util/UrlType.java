package util;

import util.error.HttpsErrorMessage;

import java.net.ProtocolException;
import java.util.Arrays;
import java.util.Optional;

public enum UrlType {
    QUERY_STRING(".*\\?(.*)"),
    TEMPLATES_FILE(".*\\.(html|ico)$"),
    STATIC_FILE(".*\\.(css|js)$"),
    ;


    private String regex;

    UrlType(String regex) {
        this.regex = regex;
    }

    public static UrlType getUrlType(String url) {
        Optional<UrlType> urlType = Arrays.stream(values())
                .filter(value -> url.matches(value.regex))
                .findAny();

        return urlType.get();
    }

}

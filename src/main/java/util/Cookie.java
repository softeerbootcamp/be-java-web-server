package util;

import request.RequestHeader;

import java.net.http.HttpHeaders;
import java.util.Optional;

public class Cookie {
    private static final String COOKIE = "Cookie";
    private String key;
    private String value;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Cookie> extractCookie(RequestHeader requestHeader) {
        String cookieSet = requestHeader.getHeaderContents().get(COOKIE);

        if (cookieSet == null) {
            return Optional.empty();
        }

        String[] splitCookie = cookieSet.split("=");
        return Optional.of(new Cookie(splitCookie[0], splitCookie[1]));
    }
}

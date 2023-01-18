package util;

import request.RequestHeader;

import java.net.http.HttpHeaders;

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

    public static Cookie extractCookie(RequestHeader requestHeader) {
        String cookieSet = requestHeader.getHeaderContents().get(COOKIE);
        String[] splitCookie = cookieSet.split("=");
        return new Cookie(splitCookie[0], splitCookie[1]);
    }
}

package http.util;

import http.common.HttpHeaders;

public class HttpHeaderParser {
    private HttpHeaderParser() {}

    public static HttpHeaders parse(String stringOfHeaders) {
        return new HttpHeaders();
    }
}

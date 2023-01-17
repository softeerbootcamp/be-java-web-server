package http.util;

import http.common.HttpHeaders;

public class HttpHeaderParser {
    private HttpHeaderParser() {}

    public static HttpHeaders parse(String stringOfHeaders) {
        HttpHeaders headers = new HttpHeaders();

        if (stringOfHeaders.equals("")) {
            return headers;
        }

        for (String strOfHeader: stringOfHeaders.split("\n")) {
            int idxOfColon = strOfHeader.indexOf(":");
            String key = strOfHeader.substring(0, idxOfColon).trim();
            String value = strOfHeader.substring(idxOfColon + 1).trim();

            if (key.equals("Cookie")) {
                parseCookie(headers, value);
            }
            headers.addHeader(key, value);
        }

        return headers;
    }

    private static void parseCookie(HttpHeaders headers, String cookies) {
        String[] cookieArr = cookies.split(";");
        for (String cookie: cookieArr) {
            String[] strings = cookie.trim().split("=");
            headers.addCookie(strings[0], strings[1]);
        }
    }
}

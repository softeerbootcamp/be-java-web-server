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
            headers.putHeader(
                    strOfHeader.substring(0, idxOfColon).trim(),
                    strOfHeader.substring(idxOfColon + 1).trim());
        }

        return headers;
    }
}

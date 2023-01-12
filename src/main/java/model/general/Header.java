package model.general;

import java.util.Arrays;

public enum Header {
    DATE("date"),
    CONNECTION("Connection"),
    CONTENT_LENGTH("Content-Length"),
    CACHE_CONTROL("Cache-Control"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_ENCODING("Content-Encoding"),
    HOST("Host"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    COOKIE("Cookie"),
    ORIGIN("Origin"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    AUTHORIZATION("Authorization"),
    SEC_CH_UA("sec-ch-ua"),
    SEC_CH_UA_MOBILE("sec-ch-ua-mobile"),
    SERVER("Server"),
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
    ALLOW("Allow"),
    CONTENT_DISPOSITION("Content-Disposition"),
    LOCATION("Location"),
    CONTENT_SECURITY_POLICY("Content-Security-Policy");

    private final String header;

    Header(String header) {
        this.header = header;
    }

    public static Header from(String header) {
        return Arrays.stream(values())
                .filter(h -> h.header.equals(header))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not Supported Header"));
    }

    public String getHeader() {
        return header;
    }
}

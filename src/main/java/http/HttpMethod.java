package http;

public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    CONNECT,
    OPTIONS,
    TRACE,
    PATCH;

    public static HttpMethod getHttpMethod(String http) {
        try {
            return valueOf(http.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("HTTP 메소드가 아닙니다.");
        }
    }
}

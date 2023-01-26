package utils.enums;

public enum HttpMethod {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(false),
    HEAD(false),
    CONNECT(false),
    OPTIONS(false),
    TRACE(false),
    PATCH(false);

    private final boolean hasBody;

    HttpMethod(boolean hasBody) {
        this.hasBody = hasBody;
    }

    public static HttpMethod getHttpMethod(String method) {
        try {
            return valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(method + "는 존재하지 않는 메서드입니다.");
        }
    }

    public boolean getHasBody() {
        return hasBody;
    }
}

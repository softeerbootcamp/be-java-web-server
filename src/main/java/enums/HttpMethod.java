package enums;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");
    private String method;
    private static Map<String, HttpMethod> cache = new HashMap<>();

    static {
        for (HttpMethod value : values()) {
            cache.put(value.getMethod(), value);
        }
    }

    HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static HttpMethod getHttpMethod(String methodName) {
        return cache.get(methodName);
    }
}

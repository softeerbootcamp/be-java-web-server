package util;

import java.util.Map;
import java.util.Objects;

public final class HttpRequestUtils {
    public HttpRequestUtils() {}


    public static Map<String, String> parseQueryString(String queryString) {
        if (Objects.isNull(queryString)) {
            return Map.of();
        }

        return Map.of();
    }

}

package http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponseHeaders {

    private final Map<String, String> headers;

    private HttpResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    public static HttpResponseHeaders createDefaultHeaders() {
        return new HttpResponseHeaders(new HashMap<>());
    }

    @Override
    public String toString() {
        return headers.keySet()
                .stream()
                .map(header -> header + ": " + getValue(header) + System.lineSeparator())
                .collect(Collectors.joining());
    }
}

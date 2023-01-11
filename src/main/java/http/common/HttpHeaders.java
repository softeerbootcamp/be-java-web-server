package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public String getValue(String key) {
        return this.headers.get(key);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addHeaders(HttpHeaders headers) {
        this.headers.putAll(headers.headers);
    }

    public int size() {
        return headers.size();
    }

    public Set<String> keys() {
        return this.headers.keySet();
    }
}

package http.common;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers;
    private final Map<String, String> cookies;

    public HttpHeaders() {
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
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

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public String getCookie(String key) {
        if (cookies.containsKey(key)) {
            return cookies.get(key);
        }

        return "";
    }

    public int size() {
        return headers.size();
    }

    public int contentLength() {
        boolean hasBody = headers.containsKey("Content-Type") && headers.containsKey("Content-Length");
        return hasBody ? Integer.parseInt(headers.get("Content-Length")) : 0;
    }

    @Override
    public String toString() {
        StringBuilder sbOfHeaders = new StringBuilder();

        for (String key: headers.keySet()) {
            sbOfHeaders.append(key).append(" : ").append(headers.get(key)).append("\r\n");
        }

        return sbOfHeaders.toString();
    }
}

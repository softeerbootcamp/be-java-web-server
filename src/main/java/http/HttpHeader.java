package http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private final Map<String, String> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String name : headers.keySet()) {
            sb.append(name).append(": ").append(headers.get(name)).append("\r\n");
        }

        return sb.toString();
    }
}

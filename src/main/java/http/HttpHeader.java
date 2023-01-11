package http;

import java.util.Map;

public class HttpHeader {
    private final Map<String, String> headers;

    private HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeader from(Map<String, String> headers) {
        return new HttpHeader(headers);
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public String getValue(String header) {
        return headers.get(header);
    }
}

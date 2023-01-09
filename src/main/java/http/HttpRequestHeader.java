package http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    private final Map<String, String> headers;

    private HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeader of(Map<String, String> headers){
        return new HttpRequestHeader(headers);
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public String getValue(String header) {
        return headers.get(header);
    }
}

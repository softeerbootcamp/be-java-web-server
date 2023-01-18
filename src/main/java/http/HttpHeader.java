package http;

import java.util.Collections;
import java.util.Map;


public class HttpHeader {
    private static final String CONTENT_LENGTH = "Content-Length";
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

    public int getContentLength() {
        if (headers.containsKey(CONTENT_LENGTH))
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        return -1;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        for (String header : headers.keySet()) {
            sb.append(String.format("%s: %s \r\n", header, headers.get(header)));
        }
        return sb.toString();
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }
}

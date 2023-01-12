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

    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        for (String header : headers.keySet()){
            sb.append(String.format("%s: %s \r\n", header, headers.get(header)));
        }
        return sb.toString();
    }
}

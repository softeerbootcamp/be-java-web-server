package Response;

import java.util.Map;
import java.util.stream.Collectors;

import static Response.HttpResponse.NEWLINE;

public class HttpResponseHeaders {
    private Map<String, String> headers;

    public HttpResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void putHeader(String key, String value) {
        this.headers.put(key, value);
    }

    @Override
    public String toString() {
        return this.headers.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(NEWLINE)) + NEWLINE;
    }
}

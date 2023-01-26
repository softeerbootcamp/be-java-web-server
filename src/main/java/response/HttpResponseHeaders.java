package response;

import util.FileIoUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static response.HttpResponse.NEWLINE;

public class HttpResponseHeaders {
    private Map<String, String> headers;

    public HttpResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpResponseHeaders of(String requestPath, int length) {
        ContentType contentType = ContentType.PLAIN;
        if (!requestPath.isEmpty()) {
            String ex = FileIoUtil.findExtension(requestPath);
            contentType = ContentType.valueOf(ex.toUpperCase());
        }

        Map<String, String> headers = new HashMap<>();          //headers
        headers.put("Content-Type", contentType.getContentText());
        headers.put("Content-Length", String.valueOf(length));
        return new HttpResponseHeaders(headers);
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

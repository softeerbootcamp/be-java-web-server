package http.request;

import http.HttpHeaders;
import http.Uri;

import java.util.Map;

public class HttpRequest {

    private static final String ENTER = "\n";

    private final HttpStartLine startLine;
    private final HttpHeaders headers;

    private HttpRequest(HttpStartLine startLine, HttpHeaders headers) {
        this.startLine = startLine;
        this.headers = headers;
    }

    public static HttpRequest from(String input) {
        String[] lines = input.split(ENTER);
        String startLine = lines[0];

        return new HttpRequest(HttpStartLine.from(startLine), HttpHeaders.of(lines));
    }

    public HttpStartLine getStartLine() {
        return startLine;
    }

    public HttpMethod getMethod() {
        return startLine.getMethod();
    }

    public Uri getUri() {
        return startLine.getUri();
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

}

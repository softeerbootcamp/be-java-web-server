package http.request;

import java.util.Map;

public class HttpRequest {
    private final HttpStartLine startLine;
    private final HttpRequestHeader requestHeader;
    private final HttpRequestBody requestBody;

    private HttpRequest(HttpStartLine startLine, HttpRequestHeader requestHeader, HttpRequestBody requestBody) {
        this.startLine = startLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(HttpStartLine startLine, HttpRequestHeader requestHeader, HttpRequestBody requestBody) {
        return new HttpRequest(startLine, requestHeader, requestBody);
    }

    public static HttpRequest ofNoBody(HttpStartLine startLine, HttpRequestHeader requestHeader) {
        return new HttpRequest(startLine, requestHeader, null);
    }

    public URI getUri() {
        return this.startLine.getUri();
    }

    public String getVersion() {
        return this.startLine.getVersion();
    }

    public Map<String, String> getQuerys() {
        return this.getUri().getQuerys();
    }
}

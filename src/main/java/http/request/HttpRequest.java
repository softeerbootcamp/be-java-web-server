package http.request;

import http.HttpHeader;

import java.util.Map;

public class HttpRequest {
    private final HttpRequestLine startLine;
    private final HttpHeader requestHeader;
    private final HttpRequestBody requestBody;

    private HttpRequest(HttpRequestLine startLine, HttpHeader requestHeader, HttpRequestBody requestBody) {
        this.startLine = startLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(HttpRequestLine startLine, HttpHeader requestHeader, HttpRequestBody requestBody) {
        return new HttpRequest(startLine, requestHeader, requestBody);
    }

    public static HttpRequest ofNoBody(HttpRequestLine startLine, HttpHeader requestHeader) {
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

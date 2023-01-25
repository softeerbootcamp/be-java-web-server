package http.request;

import http.HttpUri;

public class HttpRequestLine {
    private final String method;
    private final HttpUri uri;
    private final String version;

    public HttpRequestLine(String method, HttpUri uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public HttpUri getHttpUri() {
        return this.uri;
    }

    public String getHttpVersion() {
        return version;
    }

    public String getHttpMethod() {
        return method;
    }
}

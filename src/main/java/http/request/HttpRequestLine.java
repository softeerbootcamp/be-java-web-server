package http.request;

import http.HttpUri;

public class HttpRequestLine {
    private String method;
    private HttpUri uri;
    private String version;

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
}

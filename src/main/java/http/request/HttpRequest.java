package http.request;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URI;

import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private URI uri;
    private HttpHeaders headers;

    public HttpRequest(HttpMethod method, URI uri, HttpHeaders headers) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public URI getUri() {
        return this.uri;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public String getPath() {
        return this.uri.getPath();
    }

    public Map<String, String> getQuerys() {
        return this.uri.getQuerys();
    }
}

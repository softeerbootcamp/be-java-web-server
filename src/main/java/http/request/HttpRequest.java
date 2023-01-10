package http.request;

import http.common.HttpHeaders;
import http.common.HttpMethod;
import http.common.URI;

public class HttpRequest {
    private HttpMethod method;
    private URI uri;
    private HttpHeaders headers;

    public HttpRequest(HttpMethod method, URI uri, HttpHeaders headers) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
    }

    public HttpMethod method() {
        return this.method;
    }

    public URI getUri() {
        return this.uri;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }
}

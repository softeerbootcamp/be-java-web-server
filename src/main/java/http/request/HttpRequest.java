package http.request;

import http.common.HttpHeaders;
import http.common.URI;

public class HttpRequest {
    private URI uri;
    private HttpHeaders headers;

    public HttpRequest(URI uri, HttpHeaders headers) {
        this.uri = uri;
        this.headers = headers;
    }

    public URI getUri() {
        return uri;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}

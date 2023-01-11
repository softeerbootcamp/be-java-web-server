package http.response;

import http.common.HttpBody;
import http.common.HttpHeaders;
import http.common.HttpStatus;

public class HttpResponse {
    private HttpStatus status;
    private HttpHeaders headers;
    private HttpBody body;

    public HttpResponse() {
        this.status = HttpStatus.OK;
        this.headers = new HttpHeaders();
        this.body = new HttpBody();
    }


    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        if (this.headers == null) {
            this.headers = new HttpHeaders();
        }

        this.headers.addHeader(key, value);
    }

    public void setBody(HttpBody body) {
        this.headers.addHeader("Content-Length", String.valueOf(body.size()));
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpBody getBody() {
        return body;
    }

    public void redirect(String path) {
        this.status = HttpStatus.FOUND;
        this.headers.addHeader("Location", path);
    }
}

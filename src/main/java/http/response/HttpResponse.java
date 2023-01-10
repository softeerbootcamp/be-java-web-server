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

    public void setBody(HttpBody body) {
        this.headers.setHeader("Content-Length", String.valueOf(body.size()));
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
}

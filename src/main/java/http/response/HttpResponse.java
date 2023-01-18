package http.response;

import http.common.HttpHeaders;
import http.common.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1";

    private HttpStatus status;
    private HttpHeaders headers;
    private byte[] body;

    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.status = HttpStatus.OK;
        this.headers = new HttpHeaders();
        this.body = new byte[0];
        this.dos = dos;
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

    public void setBody(byte[] body) {
        this.headers.addHeader("Content-Length", String.valueOf(body.length));
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void redirect(String path) {
        this.status = HttpStatus.FOUND;
        this.headers.addHeader("Location", path);
    }

    public void setCookie(String value, String path) {
        headers.addHeader("Set-Cookie", value + "; Path=" + path);
    }

    public void send() throws IOException {
        String statusLine = HTTP_VERSION + " " + status.code() + " " + status.name() + "\r\n";
        dos.writeBytes(statusLine);
        String headers = this.headers.toString();
        dos.writeBytes(headers);
        dos.writeBytes("\n");
        dos.write(body);
        dos.flush();
    }
}

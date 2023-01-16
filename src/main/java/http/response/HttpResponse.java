package http.response;

import http.HttpHeader;
import utils.ContentType;
import utils.StatusCode;

import java.util.HashMap;

public class HttpResponse {
    private String version;
    private StatusCode statusCode;
    private HttpHeader headers;
    private HttpResponseBody body;

    public HttpResponse(String version) {
        this.version = version;
        this.headers = HttpHeader.from(new HashMap<>());
        this.statusCode = StatusCode.OK;
        this.body = HttpResponseBody.createBody(new byte[0]);
    }

    public byte[] getBody() {
        return body.getBody();
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setContentType(ContentType contentType) {
        this.headers.addHeader("Content-Type", contentType.getType());
    }

    public void setBody(byte[] body) {
        this.headers.addHeader("Content-Length", String.valueOf(body.length));
        this.body.setBody(body);
    }

    public void setCookie(int sid) {
        this.headers.addHeader("Set-Cookie", String.valueOf(sid).concat("; Path=/"));
    }

    public String getHeaderMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s \r\n", this.version, this.statusCode));
        sb.append(headers.getMessage());
        sb.append("\r\n");
        return sb.toString();
    }

    public void redirectHome() {
        this.statusCode = StatusCode.FOUND;
        headers.addHeader("Location", "/index.html");
    }

    public void redirectLogin() {
        this.statusCode = StatusCode.FOUND;
        headers.addHeader("Location", "/user/login_failed.html");
    }
}

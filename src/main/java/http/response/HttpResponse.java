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
        this.headers.addHeader("Set-Cookie", "sid=".concat(String.valueOf(sid)).concat("; Path=/"));
    }

    public String getHeaderMessage() {
        return String.format("%s %s \r\n", this.version, this.statusCode) +
                headers.getMessage() +
                "\r\n";
    }

    public void redirectHome() {
        this.statusCode = StatusCode.SEE_OTHER;
        headers.addHeader("Location", "/index.html");
    }

    public void redirectLogin() {
        this.statusCode = StatusCode.SEE_OTHER;
        headers.addHeader("Location", "/users/login.html");
    }

    public void redirectLoginFailed() {
        this.statusCode = StatusCode.SEE_OTHER;
        headers.addHeader("Location", "/user/login_failed.html");
    }
}

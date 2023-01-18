package http.response;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ContentType;
import utils.StatusCode;

import java.util.HashMap;
import java.util.UUID;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
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

    public void setBody(byte[] body) {
        this.headers.addHeader("Content-Length", String.valueOf(body.length));
        this.body.setBody(body);
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setContentType(ContentType contentType) {
        this.headers.addHeader("Content-Type", contentType.getType());
    }

    public void setCookie(UUID sid) {
        String value = String.format("sid=%s; Path=/; MAX-AGE=300", sid.toString());
        this.headers.addHeader("Set-Cookie", value);
    }

    public String getHeaderMessage() {
        return String.format("%s %s \r\n", this.version, this.statusCode) +
                headers.getMessage() +
                "\r\n";
    }

    public void redirect(String path) {
        this.statusCode = StatusCode.SEE_OTHER;
        headers.addHeader("Location", path);
    }
}

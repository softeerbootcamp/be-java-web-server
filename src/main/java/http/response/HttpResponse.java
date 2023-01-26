package http.response;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.enums.ContentType;
import utils.enums.StatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class HttpResponse {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String CRLF = "\r\n";
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private String version;
    private StatusCode statusCode;
    private HttpHeader headers;
    private HttpResponseBody body;
    private DataOutputStream dos;

    public HttpResponse(String version, DataOutputStream dos) {
        if (version == null || version.trim().isEmpty() || dos == null)
            throw new IllegalArgumentException("Invalid version argument passed");
        this.version = version;
        this.dos = dos;
        this.headers = HttpHeader.create();
        this.statusCode = StatusCode.OK;
        this.body = HttpResponseBody.createBody(new byte[0]);
    }

    public byte[] getBody() {
        return body.getBody();
    }

    public void setBody(byte[] body) {
        this.headers.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
        this.body.setBody(body);
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setContentType(ContentType contentType) {
        this.headers.addHeader(CONTENT_TYPE, contentType.getType());
    }

    public void setCookie(String sid) {
        String value = String.format("sid=%s; Path=/; MAX-AGE=300", sid);
        this.headers.addHeader(SET_COOKIE, value);
    }

    public String getHeaderMessage() {
        return String.format("%s %s \r\n", this.version, this.statusCode) +
                headers.getMessage() +
                CRLF;
    }

    public void forward(String path) {
        this.statusCode = StatusCode.OK;
    }

    public void redirect(String path) {
        this.statusCode = StatusCode.SEE_OTHER;
        headers.addHeader("Location", path);
    }

    public void send() throws IOException {
        dos.write(getHeaderMessage().getBytes());
        dos.write(body.getBody(), 0, body.getBodySize());
        dos.flush();
    }
}

package http.response;

import http.HttpHeader;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtils;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final HttpStatusLine statusLine;
    private final HttpHeader header;
    private final byte[] body;

    private HttpResponse(HttpResponseBuilder httpResponseBuilder) {
        this.statusLine = httpResponseBuilder.httpStatusLine;
        this.body = httpResponseBuilder.body;
        this.header = httpResponseBuilder.header;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpHeader getHeaders() {
        return header;
    }

    public void send(DataOutputStream dos) throws IOException {
        responseHeader(dos);
        responseBody(dos);
    }

    private void responseHeader(DataOutputStream dos) {
        try {
            logger.debug("send : {}", statusLine.toStringForResponse());
            logger.debug("send : {}", header.toString());
            dos.writeBytes(statusLine.toStringForResponse());
            dos.writeBytes(header.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            if (body != null) dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static class HttpResponseBuilder {
        private HttpStatusLine httpStatusLine;
        private byte[] body;
        private HttpHeader header;

        public HttpResponseBuilder() {
        }

        public HttpResponseBuilder setHttpStatusLine(HttpStatusLine httpStatusLine) {
            this.httpStatusLine = httpStatusLine;
            return this;
        }

        public HttpResponseBuilder set200Header(String contentType, byte[] body) {
            this.header = HttpResponseUtils.makeResponse200Header(contentType, body.length);
            this.body = body;
            return this;
        }

        public HttpResponseBuilder set302Header(String destination) {
            this.header = HttpResponseUtils.makeResponse302Header(destination);
            return this;
        }

        public HttpResponseBuilder setBody(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponseBuilder addCookie(String cookieValue) {
            this.header.addHeader("Set-Cookie", "sid=" + cookieValue + "; Path=/");
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }

    }
}

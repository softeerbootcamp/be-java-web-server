package http.response;

import http.ContentType;
import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private final HttpStatusLine statusLine;
    private final HttpHeader headers;
    private final HttpResponseBody responseBody;

    private HttpResponse(
            OutputStream out,
            HttpStatusLine httpStatusLine,
            HttpHeader headers,
            HttpResponseBody responseBody
    ) {
        this.dos = new DataOutputStream(out);
        this.statusLine = httpStatusLine;
        this.headers = headers;
        this.responseBody = responseBody;
    }

    public static HttpResponse createDefaultHttpResponse(OutputStream out) {
        return new HttpResponse(
                out,
                HttpStatusLine.createDefaultStatusLine(),
                HttpHeader.createDefaultHeaders(),
                HttpResponseBody.createDefaultBody()
        );
    }

    public void forward(
            HttpStatusCode statusCode,
            ContentType contentType,
            byte[] body
    ) throws IOException {
        setStatusCode(statusCode);
        set200Headers(contentType, body.length);
        setResponseBody(body);
        send();
    }

    public void sendRedirect(HttpStatusCode statusCode, String location) throws IOException {
        setStatusCode(statusCode);
        set302Headers(location);
        send();
    }

    public void do404(byte[] body) throws IOException {
        setStatusCode(HttpStatusCode.NOT_FOUND);
        dos.writeBytes(statusLine.toString());
        dos.writeBytes(System.lineSeparator());
        dos.write(body, 0, body.length);
        dos.flush();
        logger.error("Http statusLine: {}", statusLine);
    }

    public String getStatusCode() {
        return statusLine.getHttpStatusCode().getCode();
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    private void send() throws IOException {
        dos.writeBytes(statusLine.toString());
        dos.writeBytes(headers.toString());
        dos.writeBytes(System.lineSeparator());

        logger.debug("HttpResponse statusLine: {}",  statusLine);

        if (responseBody.hasBody()) {
            byte[] body = responseBody.getBody();
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

    private void set200Headers(ContentType contentType, int contentLength) {
        headers.addHeader("Content-Type", contentType.getContentType());
        headers.addHeader("Content-Length", String.valueOf(contentLength));
    }

    private void set302Headers(String location) {
        headers.addHeader("Location", location);
    }

    private void setStatusCode(HttpStatusCode statusCode) {
        statusLine.setHttpStatusCode(statusCode);
    }

    private void setResponseBody(byte[] body) {
        responseBody.setBody(body);
    }

}

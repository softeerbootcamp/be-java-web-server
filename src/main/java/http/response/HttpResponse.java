package http.response;

import http.ContentType;
import http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;
    private final HttpStatusLine statusLine;
    private final HttpHeaders headers;
    private final HttpResponseBody responseBody;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.statusLine = HttpStatusLine.createDefaultStatusLine();
        this.headers = HttpHeaders.createDefaultHeaders();
        this.responseBody = HttpResponseBody.createDefaultBody();
    }

    public void forward(ContentType contentType, byte[] body) throws IOException {
        setStatusCode(HttpStatusCode.OK);
        set200Headers(contentType, body.length);
        setResponseBody(body);
        send();
    }

    public void sendRedirect(String location) throws IOException {
        setStatusCode(HttpStatusCode.FOUND);
        set302Headers(location);
        send();
    }

    public void do404(byte[] body) throws IOException {
        setStatusCode(HttpStatusCode.NOT_FOUND);
        dos.writeBytes(statusLine.toString());
        dos.writeBytes(System.lineSeparator());
        dos.write(body, 0, body.length);
        dos.flush();
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

        Arrays.asList(String.format("status line: %s", statusLine),
                String.format("headers: %s", headers)).forEach(logger::debug);

        byte[] body = responseBody.getBody();
        if (body.length == 0) {
            dos.flush();
            return;
        }
        dos.write(body, 0, body.length);
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

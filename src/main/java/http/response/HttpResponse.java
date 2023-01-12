package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private final DataOutputStream dos;
    private final HttpStatusLine statusLine;
    private final HttpResponseHeaders headers;
    private final HttpResponseBody responseBody;

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.statusLine = HttpStatusLine.createDefaultStatusLine();
        this.headers = HttpResponseHeaders.createDefaultHeaders();
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

    public void send() throws IOException {
        dos.writeBytes(statusLine.toString());
        dos.writeBytes(headers.toString());
        dos.writeBytes(System.lineSeparator());
        logger.debug("status line: " + statusLine);
        logger.debug("headers: " + headers);
        byte[] body = responseBody.getBody();
        if(body.length == 0){
            dos.flush();
            return;
        }
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void set200Headers(ContentType contentType, int contentLength) {
        headers.addHeader("Content-Type", contentType.getContentType());
        headers.addHeader("Content-Length", String.valueOf(contentLength));
    }

    public void set302Headers(String location) {
        headers.addHeader("Location", location);
    }

    public void do404() throws IOException {
        setStatusCode(HttpStatusCode.NOT_FOUND);
        dos.writeBytes(statusLine.toString());
        dos.flush();
        logger.info("404 status line: " + statusLine.getHttpStatusCode().getMessage());
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        statusLine.setHttpStatusCode(statusCode);
    }

    public void setResponseBody(byte[] body) {
        responseBody.setBody(body);
    }

}

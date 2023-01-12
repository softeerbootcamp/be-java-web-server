package http.response;

import http.HttpHeader;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatusLine httpStatusLine;
    private final HttpHeader httpHeader = new HttpHeader();
    private byte[] body;

    public void ok(HttpRequest request) {
        setHttpStatusLine(request, HttpStatusCode.OK);
        addHttpHeader("Content-Type", request.getHttpHeader("Accept"));
    }

    public void setHttpStatusLine(HttpRequest request, HttpStatusCode statusCode) {
        this.httpStatusLine = HttpStatusLine.of(request.getHttpVersion(), statusCode);
    }

    public void addHttpHeader(String name, String values) {
        String value = values.split(",")[0];
        if (name.equals("Content-Type") && value.contains("text")) {
            value += "; charset=UTF-8";
        }
        logger.debug("HttpResponse.addHttpHeader(): value = {}", value);

        httpHeader.addHeader(name, value);
    }

    public void setBody(String viewPath) throws IOException {
        byte[] body = new byte[0];
        File file = new File(viewPath);
        if (file.exists()) {
            body = Files.readAllBytes(file.toPath());
        }

        this.body = body;
        addHttpHeader("Content-Length", String.valueOf(body.length));
    }

    public void response(DataOutputStream dos) {
        try {
            logger.debug("httpStatusLine : {}", httpStatusLine);
            logger.debug("httpHeader : {}", httpHeader);

            dos.writeBytes(httpStatusLine + "\r\n");
            dos.writeBytes(httpHeader + "");
            dos.writeBytes("\r\n");

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpStatusCode getStatusCode() {
        return httpStatusLine.getStatusCode();
    }
}

package http.response;

import http.HttpHeader;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatusLine httpStatusLine;
    private final HttpHeader httpHeader = new HttpHeader();
    private byte[] body;

    public void setHttpStatusLine(HttpRequest request, HttpStatusCode statusCode) {
        this.httpStatusLine = new HttpStatusLine(request.getHttpVersion(), statusCode);
    }

    public void addHttpHeader(String name, String value) {
        logger.debug("HttpResponse.addHttpHeader(): value= {}", value);
        this.httpHeader.addHeader(name, value.split(",")[0]);
    }

    public void setBody(byte[] body) {
        this.body = body;
        addHttpHeader("Content-Length", String.valueOf(body.length));
    }

    public void response(DataOutputStream dos) {
        try {
            dos.writeBytes(httpStatusLine + "\r\n");
            dos.writeBytes(httpHeader + "");
            dos.writeBytes("\r\n");

            logger.debug("httpStatusLine : {}", httpStatusLine);
            logger.debug("httpHeader : {}", httpHeader);

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

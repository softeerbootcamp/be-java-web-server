package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final String httpVersion;
    private final HttpStatus httpStatus;
    private final String contentType;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpResponse(
            HttpStatus httpStatus,
            String contentType,
            Map<String, String> headers,
            byte[] body,
            String httpVersion
    ) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.headers = headers;
        this.body = body;
        this.httpVersion = httpVersion;
    }

    public static HttpResponse of(
            HttpStatus httpStatus,
            String  contentType,
            Map<String, String> headers,
            byte[] body,
            String httpVersion
    ) {
        return new HttpResponse(httpStatus, contentType, headers, body, httpVersion);
    }

    public void sendResponse(DataOutputStream dos) {
        try {
            dos.writeBytes(httpVersion + " " + httpStatus.getCode() + System.lineSeparator());
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8" + System.lineSeparator());
            for (String key : headers.keySet()) {
                dos.writeBytes(key + ": " + headers.get(key) + System.lineSeparator());
            }
            dos.writeBytes("Content-Length: " + body.length + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error("While writing response " + e.getMessage());
        }
    }
}

package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String httpVersion;
    private final HttpStatus httpStatus;
    private final String contentType;
    private final Map<String, String> headers;
    private final byte[] body;
    private final DataOutputStream dos;


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


    public void response200Header(String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes(httpVersion + " " + HttpStatus.OK + System.lineSeparator());
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8" + System.lineSeparator());
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + System.lineSeparator());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String location) {
        try {
            dos.writeBytes(httpVersion + " " + HttpStatus.FOUND + System.lineSeparator());
            dos.writeBytes("Location: " + location + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response404Header() {
        try {
            dos.writeBytes(httpVersion + " " + HttpStatus.NOT_FOUND + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody() {
        try {
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

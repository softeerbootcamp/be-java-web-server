package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String httpVersion;

    private final DataOutputStream dos;

    private HttpResponse(
            String httpVersion,
            DataOutputStream dos
    ) {
        this.httpVersion = httpVersion;
        this.dos = dos;
    }

    public static HttpResponse of(
            HttpRequest httpRequest,
            DataOutputStream dos
    ) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Uri uri = requestLine.getUri();
        return new HttpResponse(
                requestLine.getVersion(),
                dos
                );
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

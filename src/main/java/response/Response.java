package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response {
    public static final String BASE_URL = "http://localhost:8080";

    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private final String path;
    private final Status status;
    private final String redirect;

    private Response(String path, Status status, String redirect) {
        this.path = path;
        this.status = status;
        this.redirect = redirect;
    }

    private void header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            redirectIfFound(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void redirectIfFound(DataOutputStream outputStream) throws IOException {
        if (status.equals(Status.FOUND)) {
            outputStream.writeBytes("Location: " + BASE_URL + redirect);
        }
    }

    private void body(DataOutputStream outputStream, byte[] body) {
        try {
            outputStream.write(body, 0, body.length);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

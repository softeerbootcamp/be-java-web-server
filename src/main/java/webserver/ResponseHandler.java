package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import static webserver.HttpStatusCode.*;
import static webserver.HttpStatusCode.FOUND;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    private final ViewResolver viewResolver;

    private final Socket connection;
    public ResponseHandler(Socket connection) {
        this.connection = connection;
        this.viewResolver = new ViewResolver();
    }

    public void response(String url) {
        try (OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            if (url.contains("?")) {
                response302Header(dos);
            } else {
                Path path = viewResolver.findFilePath(url);
                String contentType = Files.probeContentType(path);
                byte[] body = viewResolver.findActualFile(path);

                response200Header(dos, body.length, contentType);
                responseBody(dos, body);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 " + OK);
            dos.writeBytes("Content-Type: " + contentType+";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 " + FOUND);
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

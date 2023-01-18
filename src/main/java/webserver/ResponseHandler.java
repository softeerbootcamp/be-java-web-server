package webserver;

import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static model.response.HttpStatusCode.*;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private final Socket connection;

    public ResponseHandler(Socket connection) {
        this.connection = connection;
    }

    public void response(Response response) {
        try (OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            switch (response.getStatusLine().getHttpStatusCode()) {
                case OK:
                    response200Header(dos, response.getBody().length, response.getHeaders().get("Content-Type"));
                    responseBody(dos, response.getBody());
                    break;
                case FOUND:
                    response302Header(dos, response);
                    break;
                case NOT_FOUND:
                    response404Header(dos);
                    break;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 " + OK);
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, Response response) {
        try {
            dos.writeBytes("HTTP/1.1 " + FOUND);
            dos.writeBytes("Location: " + response.getHeaders().get("Location")+"\r\n");
            //TODO 리팩토링 고민
            if (response.getHeaders().containsKey("Set-Cookie")) {
                dos.writeBytes("Set-Cookie: "+ response.getHeaders().get("Set-Cookie"));
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 " + NOT_FOUND);
            dos.writeBytes("Content-Type: text/html\r\n");
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

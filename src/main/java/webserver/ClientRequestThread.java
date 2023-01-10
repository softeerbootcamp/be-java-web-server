package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.ResponseGenerator;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final ResponseGenerator responseGenerator;

    private Socket connection;

    public RequestHandler(Socket connectionSocket, ResponseGenerator responseGenerator) {
        this.connection = connectionSocket;
        this.responseGenerator = responseGenerator;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            //
            if(in.available() == 0) {
                return;
            }
            responseGenerator.generate(in, dos, connection.getPort());
            //
            //response200Header(dos, body.length);
            //responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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

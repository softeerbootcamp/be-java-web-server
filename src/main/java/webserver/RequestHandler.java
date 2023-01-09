package webserver;

import java.io.*;
import java.net.Socket;

import http.common.HttpHeaders;
import http.common.URI;
import http.request.HttpRequest;
import http.util.HttpRequestParser;
import util.ResourceUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest request = HttpRequestParser.parse(in);
            URI uri = request.getUri();
            HttpHeaders headers = request.getHeaders();

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = ResourceUtils.loadFileFromClasspath(uri.getPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            e.printStackTrace();
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

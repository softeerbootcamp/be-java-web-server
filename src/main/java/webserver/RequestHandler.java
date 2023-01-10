package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import http.repsonse.HttpResponse;
import http.request.HttpRequest;
import service.UserService;
import utils.HttpRequestGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpResponseGenerator;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private UserService userService;

    public RequestHandler(Socket connectionSocket, UserService userService) {
        this.connection = connectionSocket;
        this.userService = userService;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = HttpRequestGenerator.parseHttpMessage(br);
            HttpResponse httpResponse = HttpResponseGenerator.generateResponse(httpRequest, userService);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = httpResponse.getBody();
            dos.writeBytes(httpResponse.getResponseMessage());
            responseBody(dos, body);
        } catch (Exception e) {
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

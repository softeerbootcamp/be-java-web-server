package webserver;

import Controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

            HttpResponse httpResponse = new HttpResponse();
            Controller controller = ControllerHandler.handleController(httpRequest);
            controller.makeResponse(httpRequest, httpResponse);

            DataOutputStream dos = new DataOutputStream(out);
            httpResponse.send(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

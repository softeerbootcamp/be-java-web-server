package webserver;

import Controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ControllerMapper;
import view.ViewResolver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

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
            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = new HttpResponse();
            Controller controller = ControllerMapper.getController(httpRequest);

            String viewName = controller.process(httpRequest, httpResponse);
            String viewPath = ViewResolver.process(viewName);

            render(out, httpResponse, viewPath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void render(OutputStream out, HttpResponse httpResponse, String viewPath) throws IOException {
        byte[] body = Files.readAllBytes(new File(viewPath).toPath());
        httpResponse.setBody(body);

        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.response(dos);
    }
}

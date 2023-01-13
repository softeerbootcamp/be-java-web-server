package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controller;
import model.request.Request;
import model.response.Response;
import util.ControllerMapper;

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
            Request request = Request.from(in);

            Controller controller = ControllerMapper.selectController(request);
            Response response = controller.getResponse(request);

            DataOutputStream dos = new DataOutputStream(out);
            response.writeOutputStream(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

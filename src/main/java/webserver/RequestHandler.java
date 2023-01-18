package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.ControllerMapper;
import webserver.httpUtils.Request;
import webserver.httpUtils.RequestGetter;
import webserver.httpUtils.Response;

import java.io.*;
import java.net.Socket;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Request req;
    private Response res;
    private static RequestGetter reqGetter = new RequestGetter();
    private ControllerMapper controllerMapper = ControllerMapper.getInstance();

    private Socket connection;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            req = reqGetter.getFromInputStream(in);
            res = new Response();

            Controller controller = controllerMapper.getController(req);
            controller.exec(req, res, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

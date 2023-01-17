package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controller;
import controller.StaticController;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.RequestBodyMessage;
import view.RequestHeaderMessage;
import view.RequestMessage;
import view.RequestReader;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private Controller controller;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestReader rr = new RequestReader(in);
            rr.readRequest();
            RequestMessage requestMessage = new RequestMessage(new RequestHeaderMessage(rr.startLine), new RequestBodyMessage(rr.body));
            setController(requestMessage, out);
            logger.info(controller.toString());
            controller.control();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setController(RequestMessage requestMessage, OutputStream out){
        RequestHeaderMessage requestHeaderMessage = requestMessage.getRequestHeaderMessage();
        /*
        if (requestHeaderMessage.getContentType().contains("html")){
            controller = new TemplatesController(requestHeaderMessage, out);
            return;
        }

         */
        if (requestHeaderMessage.getHttpOnlyURL().contains(".")) {
            controller = new StaticController(requestHeaderMessage, out);
            return;
        }
        if (requestHeaderMessage.getHttpOnlyURL().startsWith("/user")) {
            controller = new UserController(requestMessage, out);
            return;
        }
    }

}

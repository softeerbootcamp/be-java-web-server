package webserver;

import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.FrontServlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final FrontServlet frontServlet = new FrontServlet();


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            Request request = new Request(in);
            Response response = new Response();

            //TODO Controller 고민, 조건에 맞게 처리될 수 있도록
            frontServlet.process(request, response);

            ResponseHandler responseHandler = new ResponseHandler(connection);
            responseHandler.response(response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

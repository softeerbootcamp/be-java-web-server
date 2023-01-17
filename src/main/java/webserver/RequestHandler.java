package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import response.ResponseHandler;

public class RequestHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection; // Client
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = ResponseHandler.controlRequestAndResponse(httpRequest);
            respondToHttpRequest(out, httpResponse);
        } catch (IOException | URISyntaxException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        }
    }

    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.respond(dos);
    }
}

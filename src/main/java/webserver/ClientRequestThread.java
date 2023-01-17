package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import response.HttpResponse;
import response.Response;

public class ClientRequestThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestThread.class);

    private final RequestHandler requestHandler;

    private Socket connection;

    private Request request;

    public ClientRequestThread(Socket connectionSocket, RequestHandler requestHandler) {
        this.connection = connectionSocket;
        this.requestHandler = requestHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            if(in.available() == 0) {
                return;
            }

            this.request = Request.from(in);
            Response response = requestHandler.handle(request, connection.getPort());

            HttpResponse.handleHttpResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

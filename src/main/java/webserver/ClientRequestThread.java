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
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            if(in.available() == 0) {
                return;
            }

            this.request = Request.from(in);

            log();

            Response response = requestHandler.handle(request);

            HttpResponse.handleHttpResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void log() {
        logger.debug("\nhandle request of port {}\n{} {} {}\nheaders: {}\nbody: {}", connection.getPort(),
                request.getMethod(), request.getResource(), request.getVersion(),
                request.getRequestHeader(),
                request.getRequestBody());
    }
}
